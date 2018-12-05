package com.ly.admin.model.dto;

import com.ly.admin.model.entity.SysRole;
import lombok.Data;

/**
 * @Author liyang
 * @Create 2018/2/20
 * 角色Dto
 */
@Data
public class RoleDTO extends SysRole {
    /**
     * 角色部门Id
     */
    private Integer roleDeptId;

    /**
     * 部门名称
     */
    private String deptName;
}
