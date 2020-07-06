package com.spring.common.cacheDao;

import com.spring.common.po.AdminRolePrivilege;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Mybatis Generator on 2020-06-27 22:58:04
 */
public interface AdminRolePrivilegeCacheDao {
    int deleteByPrimaryKey(String uk);

    int insert(AdminRolePrivilege record);

    int insertSelective(AdminRolePrivilege record);

    AdminRolePrivilege selectByPrimaryKey(String uk);

    AdminRolePrivilege selectByRolePrivilege(@Param("roleUk")String roleUk, @Param("privilegeUk")String privilegeUk);

    List<AdminRolePrivilege> listByRoleUk(String roleUk);

    int updateByPrimaryKeySelective(AdminRolePrivilege record);

    int updateByPrimaryKey(AdminRolePrivilege record);
}