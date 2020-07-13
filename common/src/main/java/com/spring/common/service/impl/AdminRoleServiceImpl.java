package com.spring.common.service.impl;

import com.spring.common.mybatis.AdminRoleMapper;
import com.spring.common.mybatis.AdminRolePrivilegeMapper;
import com.spring.common.po.AdminRole;
import com.spring.common.po.AdminRolePrivilege;
import com.spring.common.service.AdminRoleService;
import com.spring.common.utils.Moment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminRoleServiceImpl implements AdminRoleService {
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private AdminRolePrivilegeMapper adminRolePrivilegeMapper;

    @Transactional
    @Override
    public int addAdminRole(AdminRole adminRole) {
        adminRole.setCreateTime(new Moment().toDate());
        adminRole.setUpdateTime(adminRole.getCreateTime());
        return adminRoleMapper.insert(adminRole);
    }

    @Override
    public int updateAdminRoleNameAndDesc(AdminRole adminRole) {
        AdminRole targetAdminRole=AdminRole.builder()
                .uk(adminRole.getUk())
                .roleName(adminRole.getRoleName())
                .roleDesc(adminRole.getRoleDesc())
                .updateTime(new Moment().toDate())
                .build();
        return adminRoleMapper.updateByPrimaryKeySelective(targetAdminRole);
    }

    @Transactional
    @Override
    public int deleteAdminRole(AdminRole adminRole) {
        List<AdminRolePrivilege> adminRolePrivilegeList = adminRolePrivilegeMapper.listByRoleUk(adminRole.getUk());
        adminRolePrivilegeList.forEach(adminRolePrivilege -> {
            adminRolePrivilegeMapper.deleteByPrimaryKey(adminRolePrivilege.getUk());
        });
        return adminRoleMapper.deleteByPrimaryKey(adminRole.getUk());
    }

}
