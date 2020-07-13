package com.spring.webadmin.module.adminRole;

import com.spring.common.domain.condition.AdminUserCondition;
import com.spring.common.exception.ServiceException;
import com.spring.common.mybatis.AdminRoleMapper;
import com.spring.common.mybatis.AdminUserMapper;
import com.spring.common.po.AdminRole;
import com.spring.common.service.AdminRoleService;
import com.spring.common.utils.SmartBeanUtil;
import com.spring.webadmin.anno.OperateLog;
import com.spring.webadmin.constant.Route;
import com.spring.webadmin.domain.vo.ResponseDate;
import com.spring.webadmin.module.adminRole.domain.AddAdminRoleDTO;
import com.spring.webadmin.module.adminRole.domain.AdminRoleTreeVO;
import com.spring.webadmin.module.adminRole.domain.SelfSubAdminRoleVO;
import com.spring.webadmin.module.adminRole.domain.UpdateAdminRoleDTO;
import com.spring.webadmin.module.adminRole.tools.RoleToll;
import com.spring.webadmin.module.adminUser.domain.AdminInfoDTO;
import com.spring.webadmin.module.adminUser.tools.AdminTool;
import com.spring.webadmin.tools.UkTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Api(tags = "管理员角色接口")
@RestController
@RequestMapping(Route.path + Route.AdminRole.path)
public class RoleController {
    @Autowired
    private AdminRoleService adminRoleService;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private AdminUserMapper adminUserMapper;

    @ApiOperation(value = "获取管理员角色"
            , notes = "获取直属下级角色和当前角色的树形结构；" +
            "顶级角色获取所有角色结构")
    @GetMapping(Route.AdminRole.getRolesTree)
    public ResponseDate<List<AdminRoleTreeVO>> getRolesTree(HttpServletRequest httpServletRequest) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        AdminRole adminRole = adminInfoDTO.getAdminRole();
        List<AdminRoleTreeVO> adminRoleTreeVOList = null;
        if (RoleToll.isTopRole(adminRole)) {
            adminRoleTreeVOList = RoleToll.generateAllTree();
        } else {
            adminRoleTreeVOList = RoleToll.generateSelfAndSubTree(adminRole);
        }
        return ResponseDate.<List<AdminRoleTreeVO>>builder()
                .success(true)
                .data(adminRoleTreeVOList)
                .build();
    }

    @OperateLog
    @ApiOperation(value = "添加角色"
            , notes = "只能添加直属下级角色;不能添加顶级角色")
    @PostMapping(Route.AdminRole.addRole)
    public ResponseDate addRole(HttpServletRequest httpServletRequest
            , @Valid @RequestBody AddAdminRoleDTO addAdminRoleDTO) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        if (addAdminRoleDTO.getParentUk() == null) throw new IllegalArgumentException("父角色不能为空");
        if (RoleToll.isTopRole(addAdminRoleDTO.getParentUk())) throw new IllegalArgumentException("不能添加顶级角色");
        boolean hasPermission = false;
        if (RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), addAdminRoleDTO.getParentUk())
                || RoleToll.isEqualRole(adminInfoDTO.getAdminRole().getUk(), addAdminRoleDTO.getParentUk())) {
            hasPermission = true;
        }
        if (hasPermission == false) throw new IllegalArgumentException("权限不足");

        AdminRole adminRole = SmartBeanUtil.copy(addAdminRoleDTO, AdminRole.class);
        adminRole.setUk(UkTool.getUk32());
        int result = adminRoleService.addAdminRole(adminRole);
        return ResponseDate.builder()
                .success(result == 1)
                .build();
    }

    @OperateLog
    @ApiOperation(value = "修改角色", notes = "只能修改直属下级角色和当前角色")
    @PostMapping(Route.AdminRole.updateRole)
    public ResponseDate updateRole(HttpServletRequest httpServletRequest
            , @Valid @RequestBody UpdateAdminRoleDTO updateAdminRoleVO) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        AdminRole adminRoleOld = adminRoleMapper.selectByPrimaryKey(updateAdminRoleVO.getUk());
        if (adminRoleOld == null) throw new IllegalArgumentException("角色不存在");
        boolean hasPermission = false;
        if (RoleToll.isEqualRole(adminInfoDTO.getAdminRole().getUk(), adminRoleOld.getUk())//当前角色
                || RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), adminRoleOld.getUk())) {//直属下级
            hasPermission = true;
        }
        if (!hasPermission) throw new IllegalArgumentException("权限不足");

        SmartBeanUtil.copyProperties(updateAdminRoleVO, adminRoleOld);
        int result = adminRoleService.updateAdminRoleNameAndDesc(adminRoleOld);
        return ResponseDate.builder()
                .success(result == 1)
                .build();
    }

    @OperateLog
    @ApiOperation(value = "删除角色", notes = "只能删除直属下级角色")
    @GetMapping(Route.AdminRole.deleteRole)
    public ResponseDate deleteRole(HttpServletRequest httpServletRequest, @RequestParam(required = true) String roleUk) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        AdminRole delAdminRole = adminRoleMapper.selectByPrimaryKey(roleUk);
        if (delAdminRole == null) throw new IllegalArgumentException("角色不存在");
        if (RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), delAdminRole.getUk()) == false)
            throw new IllegalArgumentException("权限不足");

        if (RoleToll.getAllSubRole(delAdminRole).size() > 0)
            throw new ServiceException("包含子角色，请先删除子角色");
        AdminUserCondition adminUserCondition = AdminUserCondition.builder().build();
        adminUserCondition.setRoleUkSet(RoleToll.getSelfRoleAndAllSubRoleUk(delAdminRole));
        if (adminUserMapper.countByCondition(adminUserCondition) > 0) {
            throw new IllegalArgumentException("包含用户，请先删除用户");
        }
        int result = adminRoleService.deleteAdminRole(delAdminRole);
        return ResponseDate.builder()
                .success(result == 1)
                .build();
    }

    @ApiModelProperty(value = "获取角色", notes = "获取直属下级和当前角色")
    @GetMapping(Route.AdminRole.getAllSelfSubRole)
    public ResponseDate<List<SelfSubAdminRoleVO>> getAllSelfSubRole(HttpServletRequest httpServletRequest) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        Set<String> roleUkSet = RoleToll.getSelfRoleAndAllSubRoleUk(adminInfoDTO.getAdminRole());
        List<AdminRole> adminRoleList = new ArrayList<>();
        for (String roleUk : roleUkSet) {
            adminRoleList.add(adminRoleMapper.selectByPrimaryKey(roleUk));
        }
        List<SelfSubAdminRoleVO> selfSubAdminRoleVOList = SmartBeanUtil.copyList(adminRoleList, SelfSubAdminRoleVO.class);
        return ResponseDate.<List<SelfSubAdminRoleVO>>builder()
                .success(true)
                .data(selfSubAdminRoleVOList)
                .build();
    }

    @ApiModelProperty(value = "获取角色", notes = "获取直属下级")
    @GetMapping(Route.AdminRole.getAllSubRole)
    public ResponseDate<List<SelfSubAdminRoleVO>> getAllSubRole(HttpServletRequest httpServletRequest) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        Set<String> roleUkSet = RoleToll.getAllSubRoleUk(adminInfoDTO.getAdminRole());
        List<AdminRole> adminRoleList = new ArrayList<>();
        for (String roleUk : roleUkSet) {
            adminRoleList.add(adminRoleMapper.selectByPrimaryKey(roleUk));
        }
        List<SelfSubAdminRoleVO> selfSubAdminRoleVOList = SmartBeanUtil.copyList(adminRoleList, SelfSubAdminRoleVO.class);
        return ResponseDate.<List<SelfSubAdminRoleVO>>builder()
                .success(true)
                .data(selfSubAdminRoleVOList)
                .build();
    }
}
