package com.ly.admin.model.dto;

import com.ly.admin.model.entity.SysUser;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author liyang
 * @Create 2018/7/11
 */
@Data
public class UserInfo implements Serializable {
    /**
     * 用户基本信息
     */
    private SysUser sysUser;
    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色集合
     */
    private String[] roles;
}
