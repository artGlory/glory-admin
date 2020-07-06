package com.spring.webadmin.module.adminRole;

import com.spring.common.cacheDao.AdminRoleCacheDao;
import com.spring.common.cacheDao.AdminRolePrivilegeCacheDao;
import com.spring.common.cacheDao.AdminUserCacheDao;
import com.spring.common.domain.condition.AdminUserCondition;
import com.spring.common.exception.ServiceException;
import com.spring.common.po.AdminRole;
import com.spring.common.po.AdminRolePrivilege;
import com.spring.common.service.AdminUserService;
import com.spring.common.utils.Moment;
import com.spring.common.utils.SmartBeanUtil;
import com.spring.common.utils.SnowFlake;
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

@Slf4j
@Api(tags = "管理员角色接口")
@RestController
@RequestMapping(Route.path + Route.AdminRole.path)
public class RoleController {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminRoleCacheDao adminRoleCacheDao;
    @Autowired
    private AdminUserCacheDao adminUserCacheDao;
    @Autowired
    private AdminRolePrivilegeCacheDao adminRolePrivilegeCacheDao;

    @ApiOperation(value = "获取管理员角色", notes = "获取直属下级角色树形结构（包含自己）；顶级角色获取所有角色结构")
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
    @ApiOperation(value = "添加角色", notes = "添加管理员角色;只能添加直属下级角色;顶级角色操作所有非顶级角色")
    @PostMapping(Route.AdminRole.addRole)
    public ResponseDate addRole(HttpServletRequest httpServletRequest
            , @Valid @RequestBody AddAdminRoleDTO addAdminRoleDTO) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);

        if ((RoleToll.isTopRole(adminInfoDTO.getAdminRole()) && addAdminRoleDTO.getParentUk() != null)
                || RoleToll.isEqualRole(adminInfoDTO.getAdminRole().getUk(), addAdminRoleDTO.getParentUk())
                || RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), addAdminRoleDTO.getParentUk())
        ) {
            AdminRole adminRole = SmartBeanUtil.copy(addAdminRoleDTO, AdminRole.class);
            adminRole.setUk(UkTool.getUk32());
            adminRole.setCreateTime(new Moment().toDate());
            adminRole.setUpdateTime(adminRole.getCreateTime());
            int result = adminUserService.addAdminRole(adminRole);
            return ResponseDate.builder()
                    .success(result == 1)
                    .build();
        } else {
            throw new IllegalArgumentException("权限不足");
        }
    }

    @OperateLog
    @ApiOperation(value = "更改角色", notes = "更改管理员角色；只能修改直属下级角色；顶级角色操作所有非顶级角色")
    @PostMapping(Route.AdminRole.updateRole)
    public ResponseDate updateRole(HttpServletRequest httpServletRequest
            , @Valid @RequestBody UpdateAdminRoleDTO updateAdminRoleVO) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        AdminRole adminRoleOld = adminRoleCacheDao.selectByPrimaryKey(updateAdminRoleVO.getUk());
        if (adminRoleOld == null) throw new IllegalArgumentException("角色不存在");
        if ((RoleToll.isTopRole(adminInfoDTO.getAdminRole()) && adminRoleOld.getParentUk() != null)
                || RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), adminRoleOld.getUk())
        ) {
            SmartBeanUtil.copyProperties(updateAdminRoleVO, adminRoleOld);
            adminRoleOld.setUpdateTime(new Moment().toDate());

            int result = adminUserService.updateAdminRole(adminRoleOld);
            return ResponseDate.builder()
                    .success(result == 1)
                    .build();
        } else {
            throw new IllegalArgumentException("权限不足");
        }
    }

    @OperateLog
    @ApiOperation(value = "删除角色", notes = "删除管理员角色；只能操作直属下级角色;顶级角色操作所有非顶级角色")
    @GetMapping(Route.AdminRole.deleteRole)
    public ResponseDate deleteRole(HttpServletRequest httpServletRequest, @RequestParam(required = true) String roleUk) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        AdminRole delAdminRole = adminRoleCacheDao.selectByPrimaryKey(roleUk);
        if (delAdminRole == null) throw new IllegalArgumentException("角色不存在");
        if ((RoleToll.isTopRole(adminInfoDTO.getAdminRole()) && !RoleToll.isTopRole(delAdminRole))
                || RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), delAdminRole.getUk())
        ) {
            if (adminRoleCacheDao.listAllByParentUk(delAdminRole.getUk()).size() > 0)
                throw new ServiceException("包含子角色，请先删除子角色");
            AdminUserCondition adminUserCondition = AdminUserCondition.builder().build();
            adminUserCondition.setRoleUkList(new ArrayList<>());
            adminUserCondition.getRoleUkList().add(roleUk);
            if (adminUserCacheDao.countByCondition(adminUserCondition) > 0) {
                throw new IllegalArgumentException("包含用户，请先删除用户");
            }
            List<AdminRolePrivilege> adminRolePrivilegeList=adminRolePrivilegeCacheDao.listByRoleUk(delAdminRole.getUk());
            adminRolePrivilegeList.forEach(adminRolePrivilege -> {
                adminRolePrivilegeCacheDao.deleteByPrimaryKey(adminRolePrivilege.getUk());
            });
            int result = adminUserService.deleteAdminRole(delAdminRole);
            return ResponseDate.builder()
                    .success(result == 1)
                    .build();
        } else {
            throw new IllegalArgumentException("权限不足");
        }
    }

    @ApiModelProperty(value = "获取角色", notes = "获取角色（自身角色+直属下级角色）；顶级角色获取所有角色")
    @GetMapping(Route.AdminRole.getAllSelfSubRole)
    public ResponseDate<List<SelfSubAdminRoleVO>> getAllSelfSubRole(HttpServletRequest httpServletRequest) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
        List<AdminRole> adminRoleList = new ArrayList<>();
        if (RoleToll.isTopRole(adminInfoDTO.getAdminRole())) {
            adminRoleList = adminRoleCacheDao.listAll();
        } else {
            adminRoleList = RoleToll.getAllSubRole(adminInfoDTO.getAdminRole());
        }
        List<SelfSubAdminRoleVO> selfSubAdminRoleVOList = SmartBeanUtil.copyList(adminRoleList, SelfSubAdminRoleVO.class);
        return ResponseDate.<List<SelfSubAdminRoleVO>>builder()
                .success(true)
                .data(selfSubAdminRoleVOList)
                .build();
    }


}
