package com.spring.common.cacheDao;

import com.spring.common.domain.condition.AdminLoginLogCondition;
import com.spring.common.po.LogAdminLogin;

import java.util.List;

/**
* Created by Mybatis Generator on 2020-07-02 00:43:22
*/
public interface LogAdminLoginCacheDao {
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