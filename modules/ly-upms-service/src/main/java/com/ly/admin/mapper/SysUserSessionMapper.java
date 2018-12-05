package com.ly.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ly.admin.model.entity.SysUser;
import com.ly.common.entity.SysUserSession;

import java.util.Map;

/**
 * <p>
 * 用户会话信息 Mapper 接口
 * </p>
 *
 * @Author liyang
 * @Create 2018-08-17
 */
public interface SysUserSessionMapper extends BaseMapper<SysUserSession> {

    SysUserSession selectChannelAndOpenId(SysUserSession sysUserSession);
    SysUserSession selectChannelAndUserId(SysUserSession sysUserSession);

    Integer selectCountByAppId(Integer appId);

    void updateAvatarAndNickName(SysUser user);
}
