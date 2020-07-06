package com.spring.common.mybatis;

import com.spring.common.po.AdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Mybatis Generator on 2020-06-27 00:20:32
 */
public interface AdminRoleMapper {
    int deleteByPrimaryKey(String uk);

    int insert(AdminRole record);

    int insertSelective(AdminRole record);

    AdminRole selectByPrimaryKey(String uk);

    int updateByPrimaryKeySelective(AdminRole record);

    int updateByPrimaryKey(AdminRole record);

    List<AdminRole> listAll();

    List<AdminRole> listAllByParentUk(@Param("parentUk") String parentUk);

    long countAll();

}