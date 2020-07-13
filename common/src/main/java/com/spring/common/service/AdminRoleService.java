package com.spring.common.service;

import com.spring.common.po.AdminRole;

public interface AdminRoleService {

    /**
     * 新增管理员角色
     *
     * @param adminRole
     * @return
     */
    int addAdminRole(AdminRole adminRole);

    /**
     * 更改角色名称和描述
     */
    int updateAdminRoleNameAndDesc(AdminRole adminRole);

    /**
     * 删除管理员角色+删除角色所有的权限
     *
     * @param adminRole
     * @return
     */
    int deleteAdminRole(AdminRole adminRole);


}
