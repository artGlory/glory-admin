package com.spring.webadmin.module.adminRole.tools;

import com.spring.common.mybatis.AdminRoleMapper;
import com.spring.common.po.AdminRole;
import com.spring.common.utils.SmartBeanUtil;
import com.spring.webadmin.constant.CommonConstants;
import com.spring.webadmin.module.adminRole.domain.AdminRoleTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class RoleToll {
    private static AdminRoleMapper adminRoleMapper;

    @Autowired
    public void setAdminRoleMapper(AdminRoleMapper adminRoleMapper) {
        RoleToll.adminRoleMapper = adminRoleMapper;
    }

    /**
     * 向下生成子树
     *
     * @param adminRoleTreeVO
     */
    private static void generateSubChildrenTree(AdminRoleTreeVO adminRoleTreeVO) {
        List<AdminRole> subAdminRoleList = adminRoleMapper.listAllByParentUk(adminRoleTreeVO.getUk());
        for (AdminRole subTemp : subAdminRoleList) {
            AdminRoleTreeVO subAdminRoleTreeVO = SmartBeanUtil.copy(subTemp, AdminRoleTreeVO.class);
            adminRoleTreeVO.getChildren().add(subAdminRoleTreeVO);
            generateSubChildrenTree(subAdminRoleTreeVO);
        }
    }

    /**
     * 树形结构（自己+下级）
     *
     * @param adminRole
     * @return
     */
    public static List<AdminRoleTreeVO> generateSelfAndSubTree(AdminRole adminRole) {
        AdminRoleTreeVO adminRoleTreeVO = SmartBeanUtil.copy(adminRole, AdminRoleTreeVO.class);
        List<AdminRoleTreeVO> treeVOList = new ArrayList<>();
        treeVOList.add(adminRoleTreeVO);
        generateSubChildrenTree(adminRoleTreeVO);
        return treeVOList;
    }

    /**
     * 树形结构（全部）
     *
     * @return
     */
    public static List<AdminRoleTreeVO> generateAllTree() {
        List<AdminRoleTreeVO> treeVOList = new ArrayList<>();

        List<AdminRole> adminRoleList = listAllTopRole();
        for (AdminRole adminRole : adminRoleList) {
            AdminRoleTreeVO adminRoleTreeVO = SmartBeanUtil.copy(adminRole, AdminRoleTreeVO.class);
            treeVOList.add(adminRoleTreeVO);
            generateSubChildrenTree(adminRoleTreeVO);
        }
        return treeVOList;
    }

    /**
     * 获取直属父角色   顺序 => 父，父父，父父父
     *
     * @param adminRole
     * @return
     */
    public static List<AdminRole> getAllParents(AdminRole adminRole) {
        List<AdminRole> adminRoleList = new ArrayList<>();
        AdminRole adminRoleTemp = adminRole;
        while (isTopRole(adminRoleTemp) == false) {
            adminRoleTemp = adminRoleMapper.selectByPrimaryKey(adminRoleTemp.getParentUk());
            adminRoleList.add(adminRoleTemp);
        }
        return adminRoleList;
    }

    /**
     * 递归获取下级角色
     *
     * @param adminRole
     * @param adminRoleList
     */
    private static void addSubRole(AdminRole adminRole, List<AdminRole> adminRoleList) {
        List<AdminRole> subList = adminRoleMapper.listAllByParentUk(adminRole.getUk());
        adminRoleList.addAll(subList);
        for (AdminRole subRole : subList) {
            addSubRole(subRole, adminRoleList);
        }
    }

    /**
     * 获取所有下级角色;获取所有直属下级角色
     *
     * @param adminRole
     * @return
     */
    public static List<AdminRole> getAllSubRole(AdminRole adminRole) {
        List<AdminRole> result = new ArrayList<>();
        addSubRole(adminRole, result);
        return result;
    }

    /**
     * roleUk2 是不是 roleUk1 的直属下级
     * 角色1  是否有权限  操作角色2(角色1不能操作角色1)
     * 角色1必须位角色2的直属上级
     *
     * @param roleUk1
     * @param roleUk2
     * @return
     */
    public static boolean isDirectSubRole(String roleUk1, String roleUk2) {

        AdminRole adminRole1 = adminRoleMapper.selectByPrimaryKey(roleUk1);
        AdminRole adminRole2 = adminRoleMapper.selectByPrimaryKey(roleUk2);
        if (adminRole1 == null || adminRole2 == null) throw new IllegalArgumentException("角色不存在");

        boolean isCanOperate = false;
        List<AdminRole> parentsRoleList = RoleToll.getAllParents(adminRole2);
        for (AdminRole temp : parentsRoleList) {
            if (temp.getUk().equals(adminRole1.getUk())) {
                isCanOperate = true;
            }
        }
        if (isCanOperate) return true;
        else return false;
    }

    /**
     * 角色相等
     *
     * @param roleUk01
     * @param roleUk02
     * @return
     */
    public static boolean isEqualRole(String roleUk01, String roleUk02) {
        return roleUk01.equals(roleUk02);
    }

    /**
     * 判断是否为顶级角色
     *
     * @param adminRole
     * @return
     */
    public static boolean isTopRole(AdminRole adminRole) {
        return CommonConstants.top_uk.equals(adminRole.getParentUk());
    }

    /**
     * 判断是否为顶级角色
     *
     * @return
     */
    public static boolean isTopRole(String adminRoleUk) {
        AdminRole adminRole = adminRoleMapper.selectByPrimaryKey(adminRoleUk);
        return CommonConstants.top_uk.equals(adminRole);
    }

    /**
     * 获取所有顶级角色
     *
     * @return
     */
    public static List<AdminRole> listAllTopRole() {
        List<AdminRole> adminRoleList = adminRoleMapper.listAllByParentUk(CommonConstants.top_uk);
        return adminRoleList;
    }

    /**
     * 获取所有直属下级角色
     *
     * @param adminRole
     * @return
     */
    public static Set<String> getAllSubRoleUk(AdminRole adminRole) {
        Set<String> roleSet = new HashSet<>();
        List<AdminRole> adminRoleList = RoleToll.getAllSubRole(adminRole);
        adminRoleList.forEach(adminRoleTemp -> {
            roleSet.add(adminRoleTemp.getUk());
        });
        return roleSet;
    }

    /**
     * 获取所有直属下级角色和当前角色；顶级角色获取所有
     *
     * @param adminRole
     * @return
     */
    public static Set<String> getSelfRoleAndAllSubRoleUk(AdminRole adminRole) {
        Set<String> roleSet = new HashSet<>();
        List<AdminRole> adminRoleList = new ArrayList<>();
        adminRoleList = RoleToll.getAllSubRole(adminRole);
        adminRoleList.add(adminRole);
        adminRoleList.forEach(adminRoleTemp -> {
            roleSet.add(adminRoleTemp.getUk());
        });
        return roleSet;
    }

    public static void main(String[] args) {
    }
}
