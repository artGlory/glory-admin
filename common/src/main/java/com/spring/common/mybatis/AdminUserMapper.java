package com.spring.common.mybatis;

import com.spring.common.domain.condition.AdminUserCondition;
import com.spring.common.po.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by Mybatis Generator on 2020-06-27 11:34:21
 */
public interface AdminUserMapper {
    int deleteByPrimaryKey(String uk);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(String uk);

    AdminUser selectByUsername(@Param(value = "username") String username);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

    List<AdminUser> listByCondition(AdminUserCondition adminUserCondition);

    long countByCondition(AdminUserCondition adminUserCondition);

    int deleteGoogleKeyByPrimaryKey(String uk);

}