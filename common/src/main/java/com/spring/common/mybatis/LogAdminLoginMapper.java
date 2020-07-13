package com.spring.common.mybatis;

import com.spring.common.domain.condition.AdminLoginLogCondition;
import com.spring.common.po.LogAdminLogin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Created by Mybatis Generator on 2020-07-02 01:13:18
*/
public interface LogAdminLoginMapper {
    int deleteByPrimaryKey(String uk);

    int insert(LogAdminLogin record);

    int insertSelective(LogAdminLogin record);

    LogAdminLogin selectByPrimaryKey(String uk);

    int updateByPrimaryKeySelective(LogAdminLogin record);

    int updateByPrimaryKeyWithBLOBs(LogAdminLogin record);

    int updateByPrimaryKey(LogAdminLogin record);

    List<LogAdminLogin> listByCondition(AdminLoginLogCondition adminLoginLogCondition);

    long countByCondition(AdminLoginLogCondition adminLoginLogCondition);
}