package com.ly.auth.feign.fallback;

import com.ly.auth.feign.UserService;
import com.ly.common.entity.SysUserSession;
import com.ly.common.vo.ShopAppVo;
import com.ly.common.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author liyang
 * @Create 2018/5/31
 * 用户服务的fallback
 */
@Service
public class UserServiceFallbackImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserVO findUserByUsername(String username) {
        logger.error("调用{}异常:{}", "findUserByUsername", username);
        return null;
    }

    @Override
    public UserVO findUserByUsername(String username, String appId) {
        return null;
    }

    /**
     * 通过手机号查询用户、角色信息
     *
     * @param mobile 手机号
     * @return UserVo
     */
    @Override
    public UserVO findUserByMobile(String mobile) {
        logger.error("调用{}异常:{}", "通过手机号查询用户", mobile);
        return null;
    }

    /**
     * 根据OpenId查询用户信息
     *
     * @param openId openId
     * @return UserVo
     */
    @Override
    public UserVO findUserByOpenId(String openId) {
        logger.error("调用{}异常:{}", "通过OpenId查询用户", openId);
        return null;
    }

    @Override
    public SysUserSession saveUserSession(SysUserSession userSession) {
        logger.error("调用{}异常:{}", "保存用户会话session", userSession);
        return null;
    }

    @Override
    public UserVO findUserById(Integer userId) {
        logger.error("调用{}异常:{}", "通过userId查询用户信息", userId);
        return null;
    }

    @Override
    public UserVO findUserByWxOpenId(String wxOpenId) {
        logger.error("调用{}异常:{}", "通过wxOpenId查询用户信息", wxOpenId);
        return null;
    }

    @Override
    public UserVO createUserByUserSession(SysUserSession userSession) {
        logger.error("调用{}异常:{}", "通过用户会话信息创建用户", userSession);
        return null;
    }

    @Override
    public ShopAppVo findAppById(Integer appId) {
        logger.error("调用{}异常:{}", "通过appId查询商户信息", appId);
        return null;
    }

    @Override
    public SysUserSession findUserSessionByOpenId(SysUserSession session) {
        logger.error("调用{}异常:{}", "通过OpenId查询用户会话信息", session);
        return null;
    }

}
