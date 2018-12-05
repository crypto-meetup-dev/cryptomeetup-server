package com.ly.admin.model.dto;

import com.ly.admin.model.entity.SysUser;
import lombok.Data;

import java.util.List;

/**
 * @Author liyang
 * @Create 2018/5/5
 */
@Data
public class UserDTO extends SysUser {
    /**
     * 角色ID
     */
    private List<Integer> role;

    private Integer deptId;

    /**
     * 新密码
     */
    private String newpassword1;
}
