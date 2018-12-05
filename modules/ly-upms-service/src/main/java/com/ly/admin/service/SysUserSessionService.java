package com.ly.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.ly.common.entity.SysUserSession;

/**
 * <p>
 * 用户会话信息 服务类
 * </p>
 *
 * @Author liyang
 * @Create 2018-08-17
 */
public interface SysUserSessionService extends IService<SysUserSession> {

    SysUserSession findChannelAndOpenId(SysUserSession sysUserSession);
    SysUserSession findChannelAndUserId(SysUserSession sysUserSession);

}
