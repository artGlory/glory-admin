package com.spring.webadmin.module.adminPrivilege;


import com.spring.common.cacheDao.AdminPrivilegeCacheDao;
import com.spring.common.cacheDao.AdminRoleCacheDao;
import com.spring.common.cacheDao.AdminRolePrivilegeCacheDao;
import com.spring.common.po.AdminPrivilege;
import com.spring.common.po.AdminRole;
import com.spring.common.po.AdminRolePrivilege;
import com.spring.common.utils.Moment;
import com.spring.common.utils.SmartBeanUtil;
import com.spring.common.utils.SnowFlake;
import com.spring.webadmin.anno.OperateLog;
import com.spring.webadmin.constant.Route;
import com.spring.webadmin.domain.vo.ResponseDate;
import com.spring.webadmin.module.adminPrivilege.domain.AdminPermissionVO;
import com.spring.webadmin.module.adminPrivilege.domain.AdminPrivilegeTreeVO;
import com.spring.webadmin.module.adminPrivilege.domain.UpdateAdminPrivilegeDTO;
import com.spring.webadmin.module.adminPrivilege.tools.PrivilegeTool;
import com.spring.webadmin.module.adminRole.tools.RoleToll;
import com.spring.webadmin.module.adminUser.domain.AdminInfoDTO;
import com.spring.webadmin.module.adminUser.tools.AdminTool;
import com.spring.webadmin.tools.UkTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Api(tags = "管理员权限接口")
@RestController
@RequestMapping(Route.path + Route.AdminPrivilege.path)
public class PrivilegeController {

    @Autowired
    private AdminPrivilegeCacheDao adminPrivilegeCacheDao;
    @Autowired
    private AdminRoleCacheDao adminRoleCacheDao;
    @Autowired
    private AdminRolePrivilegeCacheDao adminRolePrivilegeCacheDao;

    @ApiOperation(value = "获取管理员权限"
            , notes = "获取当前角色所能看到的权限；roleUk必须位直属下级或者自己" +
            "；roleUk==null的时候，权限树=当前角色的权限，现有权限=角色权限" +
            "；roleUk!=nul,权限树=当前角色的权限，现有权限=roleUk的权限")
    @GetMapping(Route.AdminPrivilege.getAdminPermissionTree)
    public ResponseDate<AdminPermissionVO> getAdminPermissionTree(HttpServletRequest httpServletRequest
            , @RequestParam(required = false) String roleUk) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);
/*
当前用户角色树形结构
 */
        List<AdminPrivilegeTreeVO> adminPrivilegeTreeVOList = new ArrayList<>();
        for (String privilege : adminInfoDTO.getPermissions()) {
            AdminPrivilege adminPrivilege = adminPrivilegeCacheDao.selectByPrimaryKey(privilege);
            adminPrivilegeTreeVOList.add(SmartBeanUtil.copy(adminPrivilege, AdminPrivilegeTreeVO.class));
        }
        List<AdminPrivilegeTreeVO> treeVOList = PrivilegeTool.generateTree(adminPrivilegeTreeVOList);
/*
当前角色（roleUk为空的时候获取当前用户角色）的权限
 */
        List<String> adminPermissionList = new ArrayList<>();
        AdminRole targetAdminRole = adminRoleCacheDao.selectByPrimaryKey(roleUk);
        if (roleUk == null //查自己
                || (roleUk != null && adminInfoDTO.getAdminRole().equals(roleUk))//查自己
                || (RoleToll.isTopRole(adminInfoDTO.getAdminRole()) && RoleToll.isTopRole(targetAdminRole))//都是顶级角色的时候，权限相同，hack方法
        ) {
            for (String permission : adminInfoDTO.getPermissions()) {
                AdminPrivilege adminPrivilege = adminPrivilegeCacheDao.selectByPrimaryKey(permission);
                if (adminPrivilege.getPrivilegeType().equals(AdminPrivilege.privilege_type_button))
                    adminPermissionList.add(permission);
            }
        } else if ((RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), targetAdminRole.getUk()))//直属下级角色
                || (RoleToll.isTopRole(adminInfoDTO.getAdminRole()) && !RoleToll.isTopRole(targetAdminRole))//顶级用户查询所有下级（直属非直属）
        ) {
            List<AdminRolePrivilege> subAdminRolePrivilegeList = adminRolePrivilegeCacheDao.listByRoleUk(targetAdminRole.getUk());
            for (AdminRolePrivilege subAdminRolePrivilege : subAdminRolePrivilegeList) {
                AdminPrivilege adminPrivilege = adminPrivilegeCacheDao.selectByPrimaryKey(subAdminRolePrivilege.getPrivilegeUk());
                if (adminPrivilege.getPrivilegeType().equals(AdminPrivilege.privilege_type_button))
                    adminPermissionList.add(String.valueOf(subAdminRolePrivilege.getPrivilegeUk()));
            }
        } else {
            throw new IllegalArgumentException("权限不足");
        }

        AdminPermissionVO adminPermissionVO = AdminPermissionVO.builder()
                .adminPrivilegeTreeVOList(treeVOList)
                .checkPermission(adminPermissionList)
                .build();

        return ResponseDate.<AdminPermissionVO>builder()
                .success(true)
                .data(adminPermissionVO)
                .build();
    }

    @OperateLog
    @ApiOperation(value = "更新角色权限", notes = "更新下级角色权限；只能更改直属下级权限；顶级角色操作所有非顶级角色")
    @PostMapping(Route.AdminPrivilege.updateAdminRolePermissions)
    public ResponseDate updateAdminRolePermissions(HttpServletRequest httpServletRequest
            , @Valid @RequestBody UpdateAdminPrivilegeDTO updateAdminPrivilegeDTO) {
        AdminInfoDTO adminInfoDTO = AdminTool.getAdminUser(httpServletRequest);

        AdminRole updateAdminRole = adminRoleCacheDao.selectByPrimaryKey(updateAdminPrivilegeDTO.getRoleUk());
        if (updateAdminRole == null) throw new IllegalArgumentException("角色不存在");
        if ((RoleToll.isTopRole(adminInfoDTO.getAdminRole())) && !RoleToll.isTopRole(updateAdminRole)//顶级角色操作非顶级（直属下级，非直属下级）
                || (!RoleToll.isTopRole(updateAdminRole) && RoleToll.isDirectSubRole(adminInfoDTO.getAdminRole().getUk(), updateAdminRole.getUk()))//操作直属下级
        ) {
            Set<String> newPrivilegeSet = new HashSet<>();//新权限
            for (String privilege : updateAdminPrivilegeDTO.getPermissions()) {
                newPrivilegeSet.add(privilege);
                newPrivilegeSet.addAll(PrivilegeTool.getAllParentPrivilege(privilege));
            }
            Set<String> oldRolePrivilegeSet = new HashSet<>();//旧权限
            List<AdminRolePrivilege> oldRolePrivilegeList = adminRolePrivilegeCacheDao.listByRoleUk(updateAdminPrivilegeDTO.getRoleUk());
            for (AdminRolePrivilege adminRolePrivilege : oldRolePrivilegeList) {
                oldRolePrivilegeSet.add(adminRolePrivilege.getPrivilegeUk());
            }
            Set<String> nowAdminRolePrivilegeSet = new HashSet<>();//当前角色权限
            for (String permission : adminInfoDTO.getPermissions()) {
                nowAdminRolePrivilegeSet.add(permission);
            } //更改权限
            for (String privilege : nowAdminRolePrivilegeSet) {
                if (newPrivilegeSet.contains(privilege)) {
                    if (oldRolePrivilegeSet.contains(privilege)) {
                        continue;
                    } else {
                        AdminRolePrivilege adminRolePrivilege = AdminRolePrivilege.builder()
                                .uk(UkTool.getUk32())
                                .roleUk(updateAdminPrivilegeDTO.getRoleUk())
                                .privilegeUk(privilege)
                                .createTime(new Moment().toDate())
                                .updateTime(new Moment().toDate())
                                .build();
                        adminRolePrivilegeCacheDao.insert(adminRolePrivilege);
                    }
                } else {
                    AdminRolePrivilege adminRolePrivilege = adminRolePrivilegeCacheDao.selectByRolePrivilege(updateAdminPrivilegeDTO.getRoleUk(), privilege);
                    if (adminRolePrivilege != null) {
                        adminRolePrivilegeCacheDao.deleteByPrimaryKey(adminRolePrivilege.getUk());
                    }
                }
            }
            return ResponseDate.builder()
                    .success(true)
                    .build();
        } else {
            throw new IllegalArgumentException("权限不足");
        }
    }
}
