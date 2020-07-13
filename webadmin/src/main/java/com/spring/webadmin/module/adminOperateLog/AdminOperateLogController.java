package com.spring.webadmin.module.adminOperateLog;

import com.spring.common.domain.PageResultVO;
import com.spring.common.domain.condition.AdminOperateLogCondition;
import com.spring.common.mybatis.AdminRoleMapper;
import com.spring.common.mybatis.LogOperateMapper;
import com.spring.common.po.AdminRole;
import com.spring.common.po.LogOperate;
import com.spring.common.utils.SmartBeanUtil;
import com.spring.webadmin.constant.Route;
import com.spring.webadmin.domain.vo.ResponseDate;
import com.spring.webadmin.module.adminOperateLog.domain.PageAdminOperateLogCondition;
import com.spring.webadmin.module.adminOperateLog.domain.PageLogOperateVO;
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
@Api(tags = "管理员操作日志")
@RestController
@RequestMapping(Route.path + Route.AdminOperateLog.path)
public class AdminOperateLogController {
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private LogOperateMapper logOperateMapper;

    @ApiOperation(value = "分页查询操作日志", notes = "查询当前角色和直属下级角色")
    @PostMapping(Route.AdminOperateLog.getPageOperateLog)
    public ResponseDate<PageResultVO<List<PageLogOperateVO>>> getPageOperateLog(HttpServletRequest httpServletRequest
            , @Valid @RequestBody PageAdminOperateLogCondition pageAdminOperateLogCondition) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        AdminOperateLogCondition adminOperateLogCondition = SmartBeanUtil.copy(pageAdminOperateLogCondition, AdminOperateLogCondition.class);
        Set<String> roleSet = RoleToll.getSelfRoleAndAllSubRoleUk(adminInfoDTO.getAdminRole());
        adminOperateLogCondition.setRoleUkSet(roleSet);
        long count = logOperateMapper.countByCondition(adminOperateLogCondition);
        List<PageLogOperateVO> pageLogOperateVOList = new ArrayList<>();
        if (count > 0) {
            List<LogOperate> logOperateList = logOperateMapper.listByCondition(adminOperateLogCondition);
            pageLogOperateVOList = SmartBeanUtil.copyList(logOperateList, PageLogOperateVO.class);
        }
        pageLogOperateVOList.forEach(pageLogOperateVO -> {
            AdminRole adminRole = adminRoleMapper.selectByPrimaryKey(pageLogOperateVO.getOperatorRoleUk());
            if (adminRole != null) {
                pageLogOperateVO.setOperatorRoleName(adminRole.getRoleName());
            }
        });
        PageResultVO<List<PageLogOperateVO>> responseDate = PageResultVO.<List<PageLogOperateVO>>builder()
                .allNum(count)
                .data(pageLogOperateVOList)
                .pageSize(adminOperateLogCondition.getSize())
                .pageNum(adminOperateLogCondition.getPage())
                .build();
        return ResponseDate.<PageResultVO<List<PageLogOperateVO>>>builder()
                .success(true)
                .data(responseDate)
                .build();
    }

}
