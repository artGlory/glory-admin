package com.spring.webadmin.module.adminPrivilege.tools;


import com.spring.common.mybatis.AdminPrivilegeMapper;
import com.spring.common.po.AdminPrivilege;
import com.spring.webadmin.constant.CommonConstants;
import com.spring.webadmin.module.adminPrivilege.domain.AdminPrivilegeTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PrivilegeTool {

    private static AdminPrivilegeMapper adminPrivilegeMapper;

    @Autowired
    public void setAdminPrivilegeMapper(AdminPrivilegeMapper adminPrivilegeMapper) {
        PrivilegeTool.adminPrivilegeMapper = adminPrivilegeMapper;
    }


    public static AdminPrivilege getByPrivilegePath(String privilegePath) {
        return adminPrivilegeMapper.selectByPath(privilegePath);
    }

    /**
     * 递归生成子树
     *
     * @param adminPrivilegeTreeVO
     * @param adminPrivilegeTreeVOList
     */
    private static void generateSubChildrenTree(AdminPrivilegeTreeVO adminPrivilegeTreeVO, List<AdminPrivilegeTreeVO> adminPrivilegeTreeVOList) {
        List<AdminPrivilegeTreeVO> children = new ArrayList<>();
        for (AdminPrivilegeTreeVO temp : adminPrivilegeTreeVOList) {
            if (temp.getParentUk().equals(adminPrivilegeTreeVO.getUk())) {
                children.add(temp);
            }
        }
        Collections.sort(children, new Comparator<AdminPrivilegeTreeVO>() {
            @Override
            public int compare(AdminPrivilegeTreeVO o1, AdminPrivilegeTreeVO o2) {
                return o1.getSort().compareTo(o2.getSort());
            }
        });
        adminPrivilegeTreeVO.getChildren().addAll(children);
        adminPrivilegeTreeVO.getChildren().forEach(vo -> {
            generateSubChildrenTree(vo, adminPrivilegeTreeVOList);
        });
    }

    /**
     * 生成权限树
     *
     * @param adminPrivilegeTreeVOList
     * @return
     */
    public static List<AdminPrivilegeTreeVO> generateTree(List<AdminPrivilegeTreeVO> adminPrivilegeTreeVOList) {
        List<AdminPrivilegeTreeVO> result = new ArrayList<AdminPrivilegeTreeVO>();
        List<AdminPrivilegeTreeVO> children = new ArrayList<AdminPrivilegeTreeVO>();
        for (AdminPrivilegeTreeVO temp : adminPrivilegeTreeVOList) {
            if (isTopPrivilege(temp.getParentUk())) {
                children.add(temp);
            }
        }
        Collections.sort(children, new Comparator<AdminPrivilegeTreeVO>() {
            @Override
            public int compare(AdminPrivilegeTreeVO o1, AdminPrivilegeTreeVO o2) {
                return o1.getSort().compareTo(o2.getSort());
            }
        });
        result.addAll(children);
        result.forEach(t -> {
            generateSubChildrenTree(t, adminPrivilegeTreeVOList);
        });
        return result;
    }

    /**
     * 获取上级权限，直到顶级
     *
     * @param privilegeUk
     * @return
     */
    public static Set<String> getAllParentPrivilege(String privilegeUk) {
        Set<String> set = new HashSet<>();
        AdminPrivilege adminPrivilege = adminPrivilegeMapper.selectByPrimaryKey(privilegeUk);
        while (true) {
            adminPrivilege = adminPrivilegeMapper.selectByPrimaryKey(adminPrivilege.getParentUk());
            if (adminPrivilege == null) break;
            set.add(adminPrivilege.getUk());
            if (isTopPrivilege(adminPrivilege)) break;
        }
        return set;
    }

    public static boolean isTopPrivilege(AdminPrivilege adminPrivilege) {
        return CommonConstants.top_uk.equals(adminPrivilege.getParentUk());
    }

    public static boolean isTopPrivilege(String parentUk) {
        return CommonConstants.top_uk.equals(parentUk);
    }

}
