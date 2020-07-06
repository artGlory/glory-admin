package com.spring.common.service;

import com.spring.common.po.AdminRole;
import com.spring.common.po.AdminUser;

public interface AdminUserService {

    /**
     * 新增管理员角色
     *
     * @param adminRole
     * @return
     */
    int addAdminRole(AdminRole adminRole);

    /**
     * 更改管理员角色
     */
    int updateAdminRole(AdminRole adminRole);

    /**
     * 删除管理员角色
     *
     * @param adminRole
     * @return
     */
    int deleteAdminRole(AdminRole adminRole);

    /**
     * 新增用户
     *
     * @param adminUser
     * @return
     */
    int addAdminUser(AdminUser adminUser);

    /**
     * 修改用户
     *
     * @param adminUser
     * @return
     */
    int updateAdminUser(AdminUser adminUser);

    /**
     * 删除用户
     *
     * @param adminUser
     * @return
     */
    int deleteAdminUser(AdminUser adminUser);

}
