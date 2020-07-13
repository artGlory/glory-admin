package com.spring.webadmin.module.adminLoginLog;

import com.spring.common.domain.PageResultVO;
import com.spring.common.domain.condition.AdminLoginLogCondition;
import com.spring.common.mybatis.AdminRoleMapper;
import com.spring.common.mybatis.LogAdminLoginMapper;
import com.spring.common.po.AdminRole;
import com.spring.common.po.LogAdminLogin;
import com.spring.common.utils.SmartBeanUtil;
import com.spring.webadmin.constant.Route;
import com.spring.webadmin.domain.vo.ResponseDate;
import com.spring.webadmin.module.adminLoginLog.domain.PageAdminLoginLogCondition;
import com.spring.webadmin.module.adminLoginLog.domain.PageLogAdminLoginVO;
import com.spring.webadmin.module.adminRole.tools.RoleToll;
import com.spring.webadmin.module.adminUser.domain.AdminInfoDTO;
import com.spring.webadmin.module.adminUser.tools.AdminTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Api(tags = "管理员登陆日志")
@RestController
@RequestMapping(Route.path + Route.AdminLoginLog.path)
public class AdminLoginLogController {

    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private LogAdminLoginMapper logAdminLoginMapper;

    @ApiOperation(value = "查询用户登陆日志", notes = "查询当前角色和直属下级角色")
    @PostMapping(Route.AdminLoginLog.getPageLoginLog)
    public ResponseDate<PageResultVO<List<PageLogAdminLoginVO>>> getPageLoginLog(HttpServletRequest httpServletRequest
            , @Valid @RequestBody PageAdminLoginLogCondition pageAdminLoginLogCondition) {
        AdminLoginLogCondition adminLoginLogCondition = SmartBeanUtil.copy(pageAdminLoginLogCondition, AdminLoginLogCondition.class);
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        Set<String> roleSet = RoleToll.getSelfRoleAndAllSubRoleUk(adminInfoDTO.getAdminRole());
        adminLoginLogCondition.setRoleUkSet(roleSet);
        List<PageLogAdminLoginVO> pageLogAdminLoginVOList = new ArrayList<>();
        long count = logAdminLoginMapper.countByCondition(adminLoginLogCondition);
        if (count > 0) {
            List<LogAdminLogin> logAdminLoginList = logAdminLoginMapper.listByCondition(adminLoginLogCondition);
            pageLogAdminLoginVOList = SmartBeanUtil.copyList(logAdminLoginList, PageLogAdminLoginVO.class);
        }
        pageLogAdminLoginVOList.forEach(pageLogAdminLoginVO -> {
            AdminRole adminRole = adminRoleMapper.selectByPrimaryKey(pageLogAdminLoginVO.getUserRoleUk());
            if (adminRole != null) {
                pageLogAdminLoginVO.setUserRoleName(adminRole.getRoleName());
            }
        });
        PageResultVO<List<PageLogAdminLoginVO>> pageResultVO = PageResultVO.<List<PageLogAdminLoginVO>>builder()
                .allNum(count)
                .data(pageLogAdminLoginVOList)
                .pageNum(pageAdminLoginLogCondition.getPage())
                .pageSize(pageAdminLoginLogCondition.getSize())
                .build();
        return ResponseDate.<PageResultVO<List<PageLogAdminLoginVO>>>builder()
                .success(true)
                .data(pageResultVO)
                .build();
    }
}
