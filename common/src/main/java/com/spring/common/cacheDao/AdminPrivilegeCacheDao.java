package com.spring.common.cacheDao;

import com.spring.common.po.AdminPrivilege;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Mybatis Generator on 2020-06-27 00:20:54
 */
public interface AdminPrivilegeCacheDao {
    int deleteByPrimaryKey(String uk);

    int insert(AdminPrivilege record);

    int insertSelective(AdminPrivilege record);

    AdminPrivilege selectByPrimaryKey(String uk);

    /**
     * 支持模糊查询   %perminssionPath%
     *
     * @param privilegePath
     * @return
     */
    AdminPrivilege selectByPath(@Param("privilegePath") String privilegePath);

    List<AdminPrivilege> listAll();

    int updateByPrimaryKeySelective(AdminPrivilege record);

    int updateByPrimaryKey(AdminPrivilege record);

}