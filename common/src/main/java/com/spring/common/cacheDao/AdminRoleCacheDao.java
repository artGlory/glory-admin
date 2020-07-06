package com.spring.common.cacheDao;

import com.spring.common.po.AdminRole;

import java.util.List;

public interface AdminRoleCacheDao {
    int deleteByPrimaryKey(String uk);

    int insert(AdminRole record);

    int insertSelective(AdminRole record);

    AdminRole selectByPrimaryKey(String uk);

    int updateByPrimaryKeySelective(AdminRole record);

    int updateByPrimaryKey(AdminRole record);

    List<AdminRole> listAll();

    List<AdminRole> listAllByParentUk(String parentUk);

    Long countAll();


}