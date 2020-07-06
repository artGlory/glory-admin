package com.spring.webadmin.module.adminUser;

import com.spring.common.cacheDao.AdminRoleCacheDao;
import com.spring.common.cacheDao.AdminUserCacheDao;
import com.spring.common.cacheDao.LogAdminLoginCacheDao;
import com.spring.common.domain.PageResultVO;
import com.spring.common.domain.condition.AdminUserCondition;
import com.spring.common.po.AdminRole;
import com.spring.common.po.AdminUser;
import com.spring.common.po.LogAdminLogin;
import com.spring.common.service.AdminUserService;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Api(tags = "管理员接口")
@RestController
@RequestMapping(Route.path + Route.Admin.path)
public class UserController {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminUserCacheDao adminUserCacheDao;
    @Autowired
    private LogAdminLoginCacheDao logAdminLoginCacheDao;
    @Autowired
    private AdminRoleCacheDao adminRoleCacheDao;

    @ApiOperation(value = "登陆", notes = "用户登陆接口")
    @PostMapping(Route.Admin.adminLogin)
    @NoNeedLogin
    public ResponseDate<TokenVO> adminLogin(HttpServletRequest request
            , @Valid @RequestBody UserLoginDTO userLoginDTO
    ) {
        AdminUser adminUser = adminUserCacheDao.selectByUsername(userLoginDTO.getUsername());
        if (adminUser == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (!PasswordTool.validateAdminUserPassword(userLoginDTO.getPassword(), adminUser.getPassword())) {
            throw new IllegalArgumentException("用户密码不匹配");
        }

        TokenVO tokenVO = TokenVO.builder()
                .token(AdminTool.createAdminToken(adminUser.getUk()))
                .build();
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
            logAdminLoginCacheDao.insert(logAdminLogin);
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
                .message("")
                .build();
    }

    @OperateLog
    @ApiOperation(value = "登出", notes = "登出")
    @GetMapping(Route.Admin.adminLogout)
    public ResponseDate adminLogout(HttpServletRequest httpServletRequest) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        return ResponseDate.builder()
                .success(true)
                .build();
    }

    @ApiOperation(value = "查询用户信息", notes = "分页查询用户信息;" +
            "非顶级角色只能查询直属下级和当前角色下的用户；" +
            "顶级角色能查询所有用户")
    @PostMapping(Route.Admin.getPageAdminInfo)
    public ResponseDate<PageResultVO<List<PageAdminUserVO>>> getPageAdminInfo(HttpServletRequest httpServletRequest
            , @Valid @RequestBody PageAdminUserConditionDTO pageAdminUserConditionDTO) {
        AdminUserCondition adminUserCondition = SmartBeanUtil.copy(pageAdminUserConditionDTO, AdminUserCondition.class);

        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        List<AdminRole> selfSubAdminRoleList = RoleToll.getAllSubRole(adminInfoDTO.getAdminRole());
        selfSubAdminRoleList.add(adminInfoDTO.getAdminRole());
        if (RoleToll.isTopRole(adminInfoDTO.getAdminRole())) {
            selfSubAdminRoleList = adminRoleCacheDao.listAll();
        }
        for (AdminRole adminRole : selfSubAdminRoleList) {
            adminUserCondition.getRoleUkList().add(adminRole.getUk());
        }

        if (StringUtils.isEmpty(pageAdminUserConditionDTO.getRoleName()) == false) {
            String roleUk = null;
            for (AdminRole adminRole : selfSubAdminRoleList) {
                if (adminRole.getRoleName().equals(pageAdminUserConditionDTO.getRoleName())) {
                    roleUk = adminRole.getUk();
                }
            }
            if (roleUk != null) {
                adminUserCondition.getRoleUkList().clear();
                adminUserCondition.getRoleUkList().add(roleUk);
            }
        }
        adminUserCondition.reCalcIndex();

        List<PageAdminUserVO> pageAdminUserVOList = new ArrayList<>();
        long count = adminUserCacheDao.countByCondition(adminUserCondition);
        if (count > 0) {
            List<AdminUser> adminUserList = adminUserCacheDao.listByCondition(adminUserCondition);
            pageAdminUserVOList = SmartBeanUtil.copyList(adminUserList, PageAdminUserVO.class);
            pageAdminUserVOList.forEach(pageAdminUserVO -> {
                AdminRole adminRole = adminRoleCacheDao.selectByPrimaryKey(pageAdminUserVO.getRoleUk());
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
    @ApiModelProperty(value = "更改用户信息", notes = "更改用户信息;历史角色为管理员则不能更改；顶级用户能添加直属非直属下级；添加本级或直属下级角色")
    @PostMapping(Route.Admin.updateAdminInfo)
    public ResponseDate updateAdminInfo(HttpServletRequest httpServletRequest
            , @Valid @RequestBody UpdateAdminUserDTO updateAdminUserDTO) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);

        AdminUser adminUserOld = adminUserCacheDao.selectByPrimaryKey(updateAdminUserDTO.getUk());
        if (adminUserOld == null) throw new IllegalArgumentException("用户不存在");
        AdminRole adminRoleOld = adminRoleCacheDao.selectByPrimaryKey(adminUserOld.getRoleUk());
        if (adminRoleOld == null) throw new IllegalArgumentException("旧角色不存在");
        if (RoleToll.isTopRole(adminRoleOld)) {//历史角色位管理员，则不能更改角色
            updateAdminUserDTO.setRoleUk(adminRoleOld.getUk());
        }
        AdminRole adminRoleNew = adminRoleCacheDao.selectByPrimaryKey(updateAdminUserDTO.getRoleUk());
        if (adminRoleNew == null) throw new IllegalArgumentException("新角色不存在");

        if (RoleToll.isTopRole(adminInfoDTO.getAdminRole())//顶级角色操作所有
                || RoleToll.isEqualRole(adminInfoDTO.getAdminRole().getUk(), updateAdminUserDTO.getRoleUk())//当前角色
                || RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), updateAdminUserDTO.getRoleUk())//直属下级角色
        ) {
            adminUserOld.setRoleUk(adminRoleNew.getUk());
            adminUserOld.setUpdateTime(new Moment().toDate());
            int result = adminUserService.updateAdminUser(adminUserOld);
            return ResponseDate.builder()
                    .success(result == 1)
                    .build();
        } else {
            throw new IllegalArgumentException("权限不足");
        }
    }

    @OperateLog
    @ApiModelProperty(value = "增加用户", notes = "增加用户；")
    @PostMapping(Route.Admin.addAdminInfo)
    public ResponseDate addAdminInfo(HttpServletRequest httpServletRequest
            , @Valid @RequestBody AddAdminUserDTO addAdminUserDTO) {
        if (adminUserCacheDao.selectByUsername(addAdminUserDTO.getUsername().trim()) != null)
            throw new IllegalArgumentException("用户名已存在");
        AdminRole newAdminRole = adminRoleCacheDao.selectByPrimaryKey(addAdminUserDTO.getRoleUk());
        if (newAdminRole == null) throw new IllegalArgumentException("角色不存在");
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        if (RoleToll.isTopRole(adminInfoDTO.getAdminRole())
                || RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), newAdminRole.getUk())//直属下级角色
                || RoleToll.isEqualRole(adminInfoDTO.getAdminRole().getUk(), newAdminRole.getUk())//当前角色
        ) {
            AdminUser adminUser = AdminUser.builder()
                    .uk(UkTool.getUk32())
                    .password(PasswordTool.encrypteAdminUserPassword("a123456"))
                    .roleUk(addAdminUserDTO.getRoleUk())
                    .username(addAdminUserDTO.getUsername())
                    .createTime(new Moment().toDate())
                    .updateTime(new Moment().toDate())
                    .build();
            int result = adminUserService.addAdminUser(adminUser);
            String message = null;
            if (result == 1) message = "默认密码\"a123456\"";
            return ResponseDate.builder()
                    .success(result == 1)
                    .message(message)
                    .build();
        } else {
            throw new IllegalArgumentException("权限不足");
        }
    }

    @ApiModelProperty(value = "删除用户", notes = "删除用户")
    @OperateLog
    @GetMapping(Route.Admin.delAdminInfo)
    public ResponseDate delAdminInfo(HttpServletRequest httpServletRequest, @RequestParam(required = true) String uk) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        AdminUser adminUser = adminUserCacheDao.selectByPrimaryKey(uk);
        if (adminUser == null) throw new IllegalArgumentException("用户不存在");
        if (adminUser.getUk().equals(adminInfoDTO.getAdminUk())) throw new IllegalArgumentException("不能删除本人");
        AdminRole adminRole = adminRoleCacheDao.selectByPrimaryKey(adminUser.getRoleUk());
        if (RoleToll.isTopRole(adminRole)) throw new IllegalArgumentException("权限不足");
        if (RoleToll.isTopRole(adminInfoDTO.getAdminRole())
                || RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), adminRole.getUk())
                || RoleToll.isEqualRole(adminInfoDTO.getAdminRole().getUk(), adminRole.getUk())
        ) {
            int result = adminUserService.deleteAdminUser(adminUser);
            return ResponseDate.builder()
                    .success(result == 1)
                    .build();
        } else {
            throw new IllegalArgumentException("权限不足");
        }
    }
}
