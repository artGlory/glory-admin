package com.spring.common.service;

import com.spring.common.po.AdminUser;

public interface AdminUserService {

    /**
     * 新增用户
     *
     * @param adminUser
     * @return
     */
    int addAdminUser(AdminUser adminUser);

    /**
     * 删除用户
     *
     * @param adminUser
     * @return
     */
    int deleteAdminUser(AdminUser adminUser);

    /**
     * 修改 token
     *
     * @param adminUser
     * @return
     */
    int updateAdminToken(AdminUser adminUser);

    /**
     * 修改密码
     *
     * @param adminUser
     * @return
     */
    int updateAdminPassword(AdminUser adminUser);

    /**
     * 修改用户角色
     *
     * @param adminUser
     * @return
     */
    int updateAdminRole(AdminUser adminUser);

    /**
     * 修改googleKey
     */
    int updateAdminGoogleKey(AdminUser adminUser);

    /**
     * 修改google登陆状态
     */
    int updateAdminLoginWithGoogleStatus(AdminUser adminUser);

    /**
     * 修改用户登陆状态
     * @param adminUser
     * @return
     */
    int updateAdminLoginStatue(AdminUser adminUser);

    /**
     * 删除Google绑定
     * @param adminUser
     * @return
     */
    int deleteAdminGoogleKey(AdminUser adminUser);
}
