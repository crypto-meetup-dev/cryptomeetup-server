package com.ly.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ly.admin.mapper.SysUserSessionMapper;
import com.ly.admin.service.SysUserSessionService;
import com.ly.common.entity.SysUserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户会话信息 服务实现类
 * </p>
 *
 * @Author liyang
 * @Create 2018-08-17
 */
@Service
public class SysUserSessionServiceImpl extends ServiceImpl<SysUserSessionMapper, SysUserSession> implements SysUserSessionService {


    @Autowired
    private SysUserSessionMapper userSessionMapper;

    @Override
    public SysUserSession findChannelAndOpenId(SysUserSession sysUserSession) {
        return userSessionMapper.selectChannelAndOpenId(sysUserSession);
    }

    @Override
    public SysUserSession findChannelAndUserId(SysUserSession sysUserSession) {
        return userSessionMapper.selectChannelAndUserId(sysUserSession);
    }
}
