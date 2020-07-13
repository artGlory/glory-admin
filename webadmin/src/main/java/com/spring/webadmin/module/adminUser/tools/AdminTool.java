package com.spring.webadmin.module.adminUser.tools;

import com.spring.common.exception.ServiceException;
import com.spring.common.mybatis.*;
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
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.spring.common.constant.SystemConfigEnum.*;

@Component
public class AdminTool {

    private static AdminUserMapper adminUserMapper;
    private static SystemConfigMapper systemConfigMapper;
    private static AdminPrivilegeMapper adminPrivilegeMapper;
    private static AdminRolePrivilegeMapper adminRolePrivilegeMapper;
    private static AdminRoleMapper adminRoleMapper;

    @Autowired
    public void setAdminUserMapper(AdminUserMapper adminUserMapper) {
        AdminTool.adminUserMapper = adminUserMapper;
    }

    @Autowired
    public void setSystemConfigCacheDao(SystemConfigMapper systemConfigMapper) {
        AdminTool.systemConfigMapper = systemConfigMapper;
    }

    @Autowired
    public void setAdminPrivilegeMapper(AdminPrivilegeMapper adminPrivilegeMapper) {
        AdminTool.adminPrivilegeMapper = adminPrivilegeMapper;
    }

    @Autowired
    public void setAdminRolePrivilegeMapper(AdminRolePrivilegeMapper adminRolePrivilegeMapper) {
        AdminTool.adminRolePrivilegeMapper = adminRolePrivilegeMapper;
    }

    @Autowired
    public void setAdminRoleMapper(AdminRoleMapper adminRoleMapper) {
        AdminTool.adminRoleMapper = adminRoleMapper;
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
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(uk);
        if (adminUser == null) {
            throw new IllegalArgumentException("请重新登陆");
        }
        if (adminUser.getNowToken().equals(token) == false) {
            throw new IllegalArgumentException("请重新登陆");
        }
        if (adminUser.getForbidLogin().equals(AdminUser.forbid_login)){
            throw new IllegalArgumentException("禁止登陆,请联系管理员");
        }
        return getAdminUser(uk);
    }

    /**
     * 获取管理员信息
     *
     * @return
     */
    private static AdminInfoDTO getAdminUser(String adminUk) {
        String uk = adminUk;
        /*
        用户信息
         */
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(uk);
        if (adminUser == null) throw new ServiceException("登陆凭证过期，请重新登陆");
       /*
       系统信息
        */
        String platformName = systemConfigMapper.selectByUK(system_info_name.getConfigArea()
                , system_info_name.getConfigGroup()
                , system_info_name.getConfigKey()).getConfigValue();
        String logo = systemConfigMapper.selectByUK(system_info_logo.getConfigArea()
                , system_info_logo.getConfigGroup()
                , system_info_logo.getConfigKey()).getConfigValue();
        String copyright = systemConfigMapper.selectByUK(system_info_copyright.getConfigArea()
                , system_info_copyright.getConfigGroup()
                , system_info_copyright.getConfigKey()).getConfigValue();
       /*
       角色，权限
        */
        List<AdminPrivilege> adminPrivilegeList = new ArrayList<>();
        AdminRole adminRole = adminRoleMapper.selectByPrimaryKey(adminUser.getRoleUk());
        if (adminRole == null) throw new IllegalArgumentException("管理员角色不存在");
        if (RoleToll.isTopRole(adminRole)) {
            adminPrivilegeList = adminPrivilegeMapper.listAll();
        } else {
            Set<String> privilegeUkSet = new HashSet<>();
            List<AdminRolePrivilege> adminRolePrivilegeList = adminRolePrivilegeMapper.listByRoleUk(adminRole.getUk());
            adminRolePrivilegeList.forEach(adminRolePrivilege -> {
                privilegeUkSet.add(adminRolePrivilege.getPrivilegeUk());
            });
            for (String privilegeUk : privilegeUkSet) {
                AdminPrivilege adminPrivilege = adminPrivilegeMapper.selectByPrimaryKey(privilegeUk);
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
            permission[i] = adminPrivilegeList.get(i).getUk();
        }

        return AdminInfoDTO.builder()
                .name(adminUser.getUsername())
                .avatar("https://github.com/artGlory/IMG/blob/master/glory-admin/AdminProfilePhoto.gif")
                .systemName(platformName)
                .systemLogo(logo)
                .systemCopyright(copyright)
                .permissions(permission)
                .adminRole(adminRole)
                .adminUk(adminUser.getUk())
                .isBindGoogleAuthentication(StringUtils.isEmpty(adminUser.getGoogleKey())==false)
                .isLoginWithGoogleAuthentication(adminUser.getGoogleLogin().equals(AdminUser.google_login_master))
                .build();

    }
}
