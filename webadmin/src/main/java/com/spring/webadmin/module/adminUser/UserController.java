package com.spring.webadmin.module.adminUser;

import com.spring.common.domain.PageResultVO;
import com.spring.common.domain.condition.AdminLoginLogCondition;
import com.spring.common.domain.condition.AdminOperateLogCondition;
import com.spring.common.domain.condition.AdminUserCondition;
import com.spring.common.mybatis.AdminRoleMapper;
import com.spring.common.mybatis.AdminUserMapper;
import com.spring.common.mybatis.LogAdminLoginMapper;
import com.spring.common.mybatis.LogOperateMapper;
import com.spring.common.po.AdminRole;
import com.spring.common.po.AdminUser;
import com.spring.common.po.LogAdminLogin;
import com.spring.common.po.LogOperate;
import com.spring.common.service.AdminUserService;
import com.spring.common.utils.GoogleAuthUtils;
import com.spring.common.utils.HttpUtil;
import com.spring.common.utils.Moment;
import com.spring.common.utils.SmartBeanUtil;
import com.spring.webadmin.anno.NoNeedLogin;
import com.spring.webadmin.anno.OperateLog;
import com.spring.webadmin.constant.Route;
import com.spring.webadmin.domain.vo.ResponseDate;
import com.spring.webadmin.module.adminRole.tools.RoleToll;
import com.spring.webadmin.module.adminUser.domain.*;
import com.spring.webadmin.module.adminUser.tools.AdminTool;
import com.spring.webadmin.tools.MonipdbUtils;
import com.spring.webadmin.tools.PasswordTool;
import com.spring.webadmin.tools.UkTool;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@Api(tags = "管理员接口")
@RestController
@RequestMapping(Route.path + Route.Admin.path)
public class UserController {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private LogAdminLoginMapper logAdminLoginMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private LogOperateMapper logOperateMapper;

    @ApiOperation(value = "登陆", notes = "账号密码登陆；账号密码google登陆接口")
    @PostMapping(Route.Admin.adminLogin)
    @NoNeedLogin
    public ResponseDate<TokenVO> adminLogin(HttpServletRequest request
            , @Valid @RequestBody UserLoginDTO userLoginDTO
    ) {
        AdminUser adminUser = adminUserMapper.selectByUsername(userLoginDTO.getUsername());
        if (adminUser == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (!PasswordTool.validateAdminUserPassword(userLoginDTO.getPassword(), adminUser.getPassword())) {
            throw new IllegalArgumentException("用户密码不匹配");
        }
        /*
        两步登陆
         */
        if (adminUser.getGoogleLogin().equals(AdminUser.google_login_master)
                && StringUtils.isEmpty(userLoginDTO.getGoogleCode())) {
            return ResponseDate.<TokenVO>builder()
                    .success(true)
                    .data(TokenVO.builder().isNeedGoogleCode(true).build())
                    .build();
        }
        if (adminUser.getGoogleLogin().equals(AdminUser.google_login_master)
                && !StringUtils.isEmpty(userLoginDTO.getGoogleCode())) {
            boolean falg = GoogleAuthUtils.verify(adminUser.getGoogleKey(), Integer.parseInt(userLoginDTO.getGoogleCode()));
            if (false == falg) throw new IllegalArgumentException("google验证码错误，请重新输入");
        }

        String token = AdminTool.createAdminToken(adminUser.getUk());
        TokenVO tokenVO = TokenVO.builder()
                .token(token)
                .build();
        adminUser.setNowToken(token);
        adminUserService.updateAdminToken(adminUser);
        /*
        登陆日志
         */
        try {
            LogAdminLogin logAdminLogin = LogAdminLogin.builder()
                    .uk(UkTool.getUk32())
                    .userUk(adminUser.getUk())
                    .remoteIp(HttpUtil.getRemoteAddr(request))
                    .remoteAddress(MonipdbUtils.ip2Address(HttpUtil.getRemoteAddr(request)))
                    .remark(request.getHeader("user-agent"))
                    .userRoleUk(adminUser.getRoleUk())
                    .userName(adminUser.getUsername())
                    .createTime(new Moment().toDate())
                    .updateTime(new Moment().toDate())
                    .build();
            logAdminLoginMapper.insert(logAdminLogin);
        } catch (Exception e) {
        }
        return ResponseDate.<TokenVO>builder()
                .success(true)
                .data(tokenVO)
                .message("")
                .build();
    }

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息接口")
    @GetMapping(Route.Admin.getAdminInfo)
    public ResponseDate<UserInfoVO> getAdminInfo(HttpServletRequest httpServletRequest) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        UserInfoVO userInfoVO = SmartBeanUtil.copy(adminInfoDTO, UserInfoVO.class);
        return ResponseDate.<UserInfoVO>builder()
                .success(true)
                .data(userInfoVO)
                .build();
    }

    @OperateLog
    @ApiOperation(value = "登出", notes = "登出")
    @GetMapping(Route.Admin.adminLogout)
    public ResponseDate adminLogout(HttpServletRequest httpServletRequest) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(adminInfoDTO.getAdminUk());
        adminUser.setNowToken("log-out");
        int result = adminUserService.updateAdminToken(adminUser);
        return ResponseDate.builder()
                .success(result == 1)
                .build();
    }

    @ApiOperation(value = "分页查询用户信息", notes = "只能查询直属下级；")
    @PostMapping(Route.Admin.getPageAdminInfo)
    public ResponseDate<PageResultVO<List<PageAdminUserVO>>> getPageAdminInfo(HttpServletRequest httpServletRequest
            , @Valid @RequestBody PageAdminUserConditionDTO pageAdminUserConditionDTO) {
        AdminUserCondition adminUserCondition = SmartBeanUtil.copy(pageAdminUserConditionDTO, AdminUserCondition.class);

        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        Set<String> roleUkSet = RoleToll.getSelfRoleAndAllSubRoleUk(adminInfoDTO.getAdminRole());
        adminUserCondition.setRoleUkSet(roleUkSet);
        /*
        搜索角色
         */
        if (StringUtils.isEmpty(pageAdminUserConditionDTO.getRoleName()) == false) {
            String roleUk = null;
            adminUserCondition.getRoleUkSet().clear();
            for (String roleUkTemp : roleUkSet) {
                AdminRole adminRole = adminRoleMapper.selectByPrimaryKey(roleUkTemp);
                if (adminRole.getRoleName().equals(pageAdminUserConditionDTO.getRoleName())) {
                    roleUk = adminRole.getUk();
                    adminUserCondition.getRoleUkSet().add(roleUk);
                }
            }
        }

        List<PageAdminUserVO> pageAdminUserVOList = new ArrayList<>();
        long count = adminUserMapper.countByCondition(adminUserCondition);
        if (count > 0) {
            List<AdminUser> adminUserList = adminUserMapper.listByCondition(adminUserCondition);
            pageAdminUserVOList = SmartBeanUtil.copyList(adminUserList, PageAdminUserVO.class);
            pageAdminUserVOList.forEach(pageAdminUserVO -> {
                AdminRole adminRole = adminRoleMapper.selectByPrimaryKey(pageAdminUserVO.getRoleUk());
                if (adminRole != null) {
                    pageAdminUserVO.setRoleName(adminRole.getRoleName());
                }
            });
        }
        PageResultVO<List<PageAdminUserVO>> pageResultVO = PageResultVO.<List<PageAdminUserVO>>builder()
                .allNum(count)
                .data(pageAdminUserVOList)
                .pageNum(adminUserCondition.getPage())
                .pageSize(adminUserCondition.getSize())
                .build();

        return ResponseDate.<PageResultVO<List<PageAdminUserVO>>>builder()
                .success(true)
                .data(pageResultVO)
                .build();
    }

    @OperateLog
    @ApiOperation(value = "修改用户角色", notes = "修改前和修改后的角色必须为直属下级")
    @PostMapping(Route.Admin.updateAdminRole)
    public ResponseDate updateAdminRole(HttpServletRequest httpServletRequest
            , @Valid @RequestBody UpdateAdminRoleDTO updateAdminRoleDTO) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);

        AdminUser adminUserOld = adminUserMapper.selectByPrimaryKey(updateAdminRoleDTO.getUk());
        if (adminUserOld == null) throw new IllegalArgumentException("用户不存在");
        AdminRole adminRoleOld = adminRoleMapper.selectByPrimaryKey(adminUserOld.getRoleUk());
        if (adminRoleOld == null) throw new IllegalArgumentException("旧角色不存在");
        AdminRole adminRoleNew = adminRoleMapper.selectByPrimaryKey(updateAdminRoleDTO.getRoleUk());
        if (adminRoleNew == null) throw new IllegalArgumentException("新角色不存在");

        boolean hasPermission = false;
        if (RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), adminRoleOld.getUk())
                && RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), adminRoleNew.getUk())) {
            hasPermission = true;
        }
        if (hasPermission == false) throw new IllegalArgumentException("权限不足");

        adminUserOld.setRoleUk(adminRoleNew.getUk());
        adminUserOld.setUpdateTime(new Moment().toDate());
        int result = adminUserService.updateAdminRole(adminUserOld);
        return ResponseDate.builder()
                .success(result == 1)
                .build();
    }

    @OperateLog
    @ApiOperation(value = "增加用户", notes = "只能增加直属下级角色的用户")
    @PostMapping(Route.Admin.addAdminInfo)
    public ResponseDate addAdminInfo(HttpServletRequest httpServletRequest
            , @Valid @RequestBody AddAdminUserDTO addAdminUserDTO) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        if (adminUserMapper.selectByUsername(addAdminUserDTO.getUsername().trim()) != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        AdminRole newAdminRole = adminRoleMapper.selectByPrimaryKey(addAdminUserDTO.getRoleUk());
        if (newAdminRole == null) throw new IllegalArgumentException("角色不存在");
        if (RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), newAdminRole.getUk()) == false)
            throw new IllegalArgumentException("权限不足");

        AdminUser adminUser = AdminUser.builder()
                .uk(UkTool.getUk32())
                .password(PasswordTool.encrypteAdminUserPassword("a123456"))
                .roleUk(addAdminUserDTO.getRoleUk())
                .username(addAdminUserDTO.getUsername())
                .googleLogin(AdminUser.google_login_not_master)
                .forbidLogin(AdminUser.not_forbid_login)
                .build();
        int result = adminUserService.addAdminUser(adminUser);
        String message = null;
        if (result == 1) message = "默认密码\"a123456\"";
        return ResponseDate.builder()
                .success(result == 1)
                .message(message)
                .build();
    }

    @ApiOperation(value = "删除用户", notes = "只能删除直属下级角色")
    @OperateLog
    @GetMapping(Route.Admin.delAdminInfo)
    public ResponseDate delAdminInfo(HttpServletRequest httpServletRequest, @RequestParam(required = true) String uk) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(uk);
        if (adminUser == null) throw new IllegalArgumentException("用户不存在");
        if (RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), adminUser.getRoleUk()) == false)
            throw new IllegalArgumentException("权限不足");

        int result = adminUserService.deleteAdminUser(adminUser);
        return ResponseDate.builder()
                .success(result == 1)
                .build();
    }

    @ApiOperation(value = "修改管理员密码", notes = "只能修改直属下级角色的密码")
    @OperateLog
    @PostMapping(Route.Admin.updateAdminPassword)
    public ResponseDate updateAdminPassword(HttpServletRequest httpServletRequest
            , @Valid @RequestBody UpdateAdminUserPasswordVO updateAdminUserPasswordVO
    ) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        AdminUser targetAdminUser = adminUserMapper.selectByPrimaryKey(updateAdminUserPasswordVO.getUk());
        if (targetAdminUser == null) throw new IllegalArgumentException("用户不存在");
        if (RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), targetAdminUser.getRoleUk()) == false)
            throw new IllegalArgumentException("权限不足");

        targetAdminUser.setPassword(PasswordTool.encrypteAdminUserPassword(updateAdminUserPasswordVO.getPasswordNew()));
        int result = adminUserService.updateAdminPassword(targetAdminUser);

        return ResponseDate.builder()
                .success(result == 1)
                .build();
    }

    @ApiOperation(value = "修改个人密码", notes = "只能修改自己的密码")
    @OperateLog
    @PostMapping(Route.Admin.updateSelfPassword)
    public ResponseDate updateSelfPassword(HttpServletRequest httpServletRequest
            , @Valid @RequestBody UpdateSelfPasswordVO updateSelfPasswordVO
    ) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        AdminUser adminUser = adminUserMapper.selectByUsername(adminInfoDTO.getName());
        if (adminUser == null) throw new IllegalArgumentException("用户不存在");
        if (!PasswordTool.validateAdminUserPassword(updateSelfPasswordVO.getPasswordOld(), adminUser.getPassword())) {
            throw new IllegalArgumentException("旧密码不匹配");
        }
        adminUser.setPassword(PasswordTool.encrypteAdminUserPassword(updateSelfPasswordVO.getPasswordNew()));
        int result = adminUserService.updateAdminPassword(adminUser);

        return ResponseDate.builder()
                .success(result == 1)
                .build();
    }

    @ApiOperation(value = "查看操作时间轴", notes = "操作自己的")
    @GetMapping(Route.Admin.getSelfOperateTimeline)
    public ResponseDate<List<OperateTimelineVO>> getSelfOperateTimeline(HttpServletRequest httpServletRequest) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        List<OperateTimelineVO> operateTimelineVOList = new ArrayList<>();
        Date sToday = new Moment().startOf("day").toDate();
        Date eToday = new Moment().add(1, "days").startOf("day").toDate();
        /*
        登陆日志
         */
        AdminLoginLogCondition adminLoginLogCondition = new AdminLoginLogCondition();
        adminLoginLogCondition.setStartTime(sToday);
        adminLoginLogCondition.setEndTime(eToday);
        adminLoginLogCondition.setUserName(adminInfoDTO.getName());
        List<LogAdminLogin> logAdminLoginList = logAdminLoginMapper.listByCondition(adminLoginLogCondition);
        logAdminLoginList.forEach(logAdminLogin -> {
            OperateTimelineVO operateTimelineVO = OperateTimelineVO.builder()
                    .content("登陆：" + logAdminLogin.getRemoteAddress() + "," + logAdminLogin.getRemoteIp())
                    .logTime(logAdminLogin.getCreateTime())
                    .size("large")
                    .color("#67C23A")
                    .icon("el-icon-map-location")
                    .build();
            operateTimelineVOList.add(operateTimelineVO);
        });
        /*
        操作日志
         */
        AdminOperateLogCondition adminOperateLogCondition = new AdminOperateLogCondition();
        adminOperateLogCondition.setStartTime(sToday);
        adminOperateLogCondition.setEndTime(eToday);
        adminOperateLogCondition.setOperator(adminInfoDTO.getName());
        List<LogOperate> logOperateList = logOperateMapper.listByCondition(adminOperateLogCondition);
        logOperateList.forEach(logOperate -> {
            String color = logOperate.getResult().equals(LogOperate.result_exception) ? "#F56C6C" : "";
            String content = logOperate.getResult().equals(LogOperate.result_normal) ?
                    logOperate.getOperateName() : "失败：" + logOperate.getOperateName();
            OperateTimelineVO operateTimelineVO = OperateTimelineVO.builder()
                    .content(content)
                    .logTime(logOperate.getCreateTime())
                    .size("large")
                    .color(color)
                    .build();
            operateTimelineVOList.add(operateTimelineVO);
        });
        /*
        排序-时间倒序
         */
        operateTimelineVOList.sort(new Comparator<OperateTimelineVO>() {
            @Override
            public int compare(OperateTimelineVO o1, OperateTimelineVO o2) {
                return o2.getLogTime().compareTo(o1.getLogTime());
            }
        });
        return ResponseDate.<List<OperateTimelineVO>>builder()
                .success(true)
                .data(operateTimelineVOList)
                .build();
    }

    @OperateLog
    @ApiOperation(value = "获取Google身份验证器二维码", notes = "操作自己的")
    @GetMapping(Route.Admin.getGoogleKey)
    public ResponseDate<GoogleKeyVO> getGoogleKey(HttpServletRequest httpServletRequest) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(adminInfoDTO.getAdminUk());
        if (StringUtils.isEmpty(adminUser.getGoogleKey()) == false) {
            throw new IllegalArgumentException("已绑定 Google 身份验证器");
        }
        GoogleAuthenticatorKey googleAuthenticatorKey = GoogleAuthUtils.createCredentials();
        String googleKey = GoogleAuthUtils.getKey(googleAuthenticatorKey);
        String img = GoogleAuthUtils.getOtpAuthURL(adminInfoDTO.getSystemName(), adminInfoDTO.getName(), googleAuthenticatorKey);
        GoogleKeyVO googleKeyVO = GoogleKeyVO.builder()
                .googleKey(googleKey)
                .googleKeyPicture(img)
                .build();

        return ResponseDate.<GoogleKeyVO>builder()
                .success(true)
                .data(googleKeyVO)
                .build();
    }

    @OperateLog
    @ApiOperation(value = "绑定Google身份验证器", notes = "操作自己的")
    @PostMapping(Route.Admin.bindGoogleKey)
    public ResponseDate bindGoogleKey(HttpServletRequest httpServletRequest
            , @Valid @RequestBody GoogleKeyDTO googleKeyDTO) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(adminInfoDTO.getAdminUk());
        if (StringUtils.isEmpty(adminUser.getGoogleKey()) == false) {
            throw new IllegalArgumentException("已绑定 Google 身份验证器");
        }
        boolean falg = GoogleAuthUtils.verify(googleKeyDTO.getGoogleKey(), Integer.parseInt(googleKeyDTO.getGoogleCode()));
        if (falg == false) {
            throw new IllegalArgumentException("动态密码错误，请重新输入");
        }
        adminUser.setGoogleKey(googleKeyDTO.getGoogleKey());
        int result = adminUserService.updateAdminGoogleKey(adminUser);
        return ResponseDate.builder()
                .success(result == 1)
                .build();
    }

    @OperateLog
    @ApiOperation(value = "开启或关闭Google两步登陆", notes = "操作自己的")
    @GetMapping(Route.Admin.loginWithGooleAuthentication)
    public ResponseDate loginWithGooleAuthentication(HttpServletRequest httpServletRequest
            , @RequestParam(value = "loginWithGoogle") boolean loginWithGoogle) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        if (adminInfoDTO.getIsBindGoogleAuthentication() == false) {
            throw new IllegalArgumentException("请先绑定googleAuthentication");
        }
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(adminInfoDTO.getAdminUk());
        if (loginWithGoogle != adminInfoDTO.getIsLoginWithGoogleAuthentication()) {
            adminUser.setGoogleLogin(loginWithGoogle ? AdminUser.google_login_master : AdminUser.google_login_not_master);
            adminUserService.updateAdminLoginWithGoogleStatus(adminUser);
        }
        return ResponseDate.builder()
                .success(true)
                .build();
    }

    @OperateLog
    @ApiOperation(value = "禁止或允许用户登陆", notes = "只能操作直属下级角色")
    @GetMapping(Route.Admin.forbidAdminLogin)
    public ResponseDate forbidAdminLogin(HttpServletRequest httpServletRequest
            , @RequestParam(required = true) String uk) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(uk);
        if (adminUser == null) throw new IllegalArgumentException("用户不存在");
        if (RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), adminUser.getRoleUk()) == false)
            throw new IllegalArgumentException("权限不足");
        int newForbidStatus = adminUser.getForbidLogin().equals(AdminUser.forbid_login)
                ? AdminUser.not_forbid_login : AdminUser.forbid_login;
        adminUser.setForbidLogin(newForbidStatus);
        int result = adminUserService.updateAdminLoginStatue(adminUser);

        return ResponseDate.builder()
                .success(result == 1)
                .build();
    }

    @OperateLog
    @ApiOperation(value = "删除Google绑定", notes = "只能操作直属下级角色")
    @GetMapping(Route.Admin.deleteAdminGoogleKey)
    public ResponseDate deleteAdminGoogleKey(HttpServletRequest httpServletRequest
            , @RequestParam(required = true) String uk) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        AdminUser targetAdminUser = adminUserMapper.selectByPrimaryKey(uk);
        if (targetAdminUser == null) throw new IllegalArgumentException("用户不存在");
        if (RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), targetAdminUser.getRoleUk()) == false)
            throw new IllegalArgumentException("权限不足");
        int result = adminUserService.deleteAdminGoogleKey(targetAdminUser);
        return ResponseDate.builder()
                .success(result == 1)
                .build();
    }

}
