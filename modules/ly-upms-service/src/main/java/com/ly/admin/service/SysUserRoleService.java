package com.ly.admin.service;


import com.baomidou.mybatisplus.service.IService;
import com.ly.admin.model.entity.SysUserRole;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @Author liyang
 * @Create 2017-4-29
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据用户Id删除该用户的角色关系
     */
    Boolean deleteByUserId(Integer userId);
}
