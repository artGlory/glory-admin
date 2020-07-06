package com.spring.webadmin.module.adminUser.tools;

import com.spring.common.cacheDao.*;
import com.spring.common.exception.ServiceException;
import com.spring.common.po.AdminPrivilege;
import com.spring.common.po.AdminRole;
import com.spring.common.po.AdminRolePrivilege;
import com.spring.common.po.AdminUser;
import com.spring.webadmin.constant.CommonConstants;
import com.spring.webadmin.module.adminRole.tools.RoleToll;
import com.spring.webadmin.module.adminUser.domain.AdminInfoDTO;
import com.spring.webadmin.tools.TokenTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.spring.common.constant.SystemConfigEnum.*;

@Component
public class AdminTool {

    private static AdminUserCacheDao adminUserCacheDao;
    private static SystemConfigCacheDao systemConfigCacheDao;
    private static AdminPrivilegeCacheDao adminPrivilegeCacheDao;
    private static AdminRolePrivilegeCacheDao adminRolePrivilegeCacheDao;
    private static AdminRoleCacheDao adminRoleCacheDao;

    @Autowired
    private void setAdminUserCacheDao(AdminUserCacheDao adminUserCacheDao) {
        AdminTool.adminUserCacheDao = adminUserCacheDao;
    }

    @Autowired
    private void setSystemConfigCacheDao(SystemConfigCacheDao systemConfigCacheDao) {
        AdminTool.systemConfigCacheDao = systemConfigCacheDao;
    }

    @Autowired
    private void setAdminPrivilegeCacheDao(AdminPrivilegeCacheDao adminPrivilegeCacheDao) {
        AdminTool.adminPrivilegeCacheDao = adminPrivilegeCacheDao;
    }

    @Autowired
    private void setAdminRolePrivilegeCacheDao(AdminRolePrivilegeCacheDao adminRolePrivilegeCacheDao) {
        AdminTool.adminRolePrivilegeCacheDao = adminRolePrivilegeCacheDao;
    }

    @Autowired
    private void setAdminRoleCacheDao(AdminRoleCacheDao adminRoleCacheDao) {
        AdminTool.adminRoleCacheDao = adminRoleCacheDao;
    }

    /**
     * 创建token
     *
     * @param adminUserUk 管理员唯一标识
     * @return
     */
    public static String createAdminToken(String adminUserUk) {
        return TokenTool.createToken(adminUserUk);
    }

    /**
     * 获取管理员信息
     *
     * @return
     */
    public static AdminInfoDTO getAdminUser(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(CommonConstants.admin_token_key);
        if (token == null)
            throw new IllegalArgumentException("请登陆");
        String uk = TokenTool.getAdminUserUk(token);
        return getAdminUser(uk);
    }

    /**
     * 获取管理员信息
     *
     * @return
     */
    public static AdminInfoDTO getAdminUser(String adminUk) {
        String uk = adminUk;
        /*
        用户信息
         */
        AdminUser adminUser = adminUserCacheDao.selectByPrimaryKey(uk);
        if (adminUser == null) throw new ServiceException("登陆凭证过期，请重新登陆");
       /*
       系统信息
        */
        String platformName = systemConfigCacheDao.selectByUK(system_info_name.getConfigArea()
                , system_info_name.getConfigGroup()
                , system_info_name.getConfigKey()).getConfigValue();
        String logo = systemConfigCacheDao.selectByUK(system_info_logo.getConfigArea()
                , system_info_logo.getConfigGroup()
                , system_info_logo.getConfigKey()).getConfigValue();
        String copyright = systemConfigCacheDao.selectByUK(system_info_copyright.getConfigArea()
                , system_info_copyright.getConfigGroup()
                , system_info_copyright.getConfigKey()).getConfigValue();
       /*
       角色，权限
        */
        List<AdminPrivilege> adminPrivilegeList = new ArrayList<>();
        AdminRole adminRole = adminRoleCacheDao.selectByPrimaryKey(adminUser.getRoleUk());
        if (adminRole == null) throw new IllegalArgumentException("管理员角色不存在");
        if (RoleToll.isTopRole(adminRole)) {
            adminPrivilegeList = adminPrivilegeCacheDao.listAll();
        } else {
            Set<String> privilegeUkSet = new HashSet<>();
            List<AdminRolePrivilege> adminRolePrivilegeList = adminRolePrivilegeCacheDao.listByRoleUk(adminRole.getUk());
            adminRolePrivilegeList.forEach(adminRolePrivilege -> {
                privilegeUkSet.add(adminRolePrivilege.getPrivilegeUk());
            });
            for (String privilegeUk : privilegeUkSet) {
                AdminPrivilege adminPrivilege = adminPrivilegeCacheDao.selectByPrimaryKey(privilegeUk);
                adminPrivilegeList.add(adminPrivilege);
            }
        }
        Collections.sort(adminPrivilegeList, new Comparator<AdminPrivilege>() { //前端路由排序
            @Override
            public int compare(AdminPrivilege o1, AdminPrivilege o2) {
                return o1.getSort().compareTo(o2.getSort());
            }
        });
        String[] permission = new String[adminPrivilegeList.size()];
        for (int i = 0; i < adminPrivilegeList.size(); i++) {
            permission[i] = String.valueOf(adminPrivilegeList.get(i).getUk());
        }

        return AdminInfoDTO.builder()
                .name(adminUser.getUsername())
                .avatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
                .systemName(platformName)
                .systemLogo(logo)
                .systemCopyright(copyright)
                .permissions(permission)
                .adminRole(adminRole)
                .adminUk(adminUser.getUk())
                .build();

    }
}
