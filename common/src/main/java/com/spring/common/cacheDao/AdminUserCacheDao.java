package com.spring.common.cacheDao;

import com.spring.common.domain.condition.AdminUserCondition;
import com.spring.common.po.AdminUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Mybatis Generator on 2020-06-27 11:34:21
 */
public interface AdminUserCacheDao {
    int deleteByPrimaryKey(String uk);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(String uk);

    AdminUser selectByUsername(@Param(value = "username") String username);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

    List<AdminUser> listByCondition(AdminUserCondition adminUserCondition);

    Long countByCondition(AdminUserCondition adminUserCondition);
}