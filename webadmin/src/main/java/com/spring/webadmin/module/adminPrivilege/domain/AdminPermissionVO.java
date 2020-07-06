package com.spring.webadmin.module.adminPrivilege.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class AdminPermissionVO {
    @ApiModelProperty(value = "权限树")
    private List<AdminPrivilegeTreeVO> adminPrivilegeTreeVOList;
    @ApiModelProperty(value = "管理员现有权限列表")
    private List<String> checkPermission;

}
