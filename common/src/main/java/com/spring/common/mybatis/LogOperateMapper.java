package com.spring.common.mybatis;

import com.spring.common.domain.condition.AdminOperateLogCondition;
import com.spring.common.po.LogOperate;

import java.util.List;

/**
* Created by Mybatis Generator on 2020-07-01 17:33:03
*/
public interface LogOperateMapper {
    int deleteByPrimaryKey(String uk);

    int insert(LogOperate record);

    int insertSelective(LogOperate record);

    LogOperate selectByPrimaryKey(String uk);

    int updateByPrimaryKeySelective(LogOperate record);

    int updateByPrimaryKeyWithBLOBs(LogOperate record);

    int updateByPrimaryKey(LogOperate record);

    List<LogOperate> listByCondition(AdminOperateLogCondition adminOperateLogCondition);

    long countByCondition(AdminOperateLogCondition adminOperateLogCondition);
}