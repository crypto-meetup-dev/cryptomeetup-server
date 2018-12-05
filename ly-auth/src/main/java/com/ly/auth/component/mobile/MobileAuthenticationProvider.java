package com.ly.auth.component.mobile;

import com.alibaba.fastjson.JSON;
import com.ly.auth.exception.WeChatNotBindException;
import com.ly.auth.feign.UserService;
import com.ly.auth.util.UserDetailsImpl;
import com.ly.common.bean.body.MobileLoginBody;
import com.ly.common.bean.body.WeChatSessionBody;
import com.ly.common.entity.SysUserSession;
import com.ly.common.util.HttpClientUtil;
import com.ly.common.vo.ShopAppVo;
import com.ly.common.vo.SysRole;
import com.ly.common.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author liyang
 * @Create 2018/1/9
 * 手机号登录校验逻辑
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {
    protected org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;
        MobileLoginBody loginBody = (MobileLoginBody) mobileAuthenticationToken.getPrincipal();
        UserVO userVo;
        if (loginBody.isMobileWeChatLogin()) {
            SysUserSession userSession = findUserSessionByWeChat(loginBody);
            if (userSession.getUser() == null) {
                throw new WeChatNotBindException("微信未绑定:" + loginBody.getChannel_key());
            }
            userVo = userSession.getUser();
        } else if (loginBody.isEOS()) {
            SysUserSession userSession = findUserSessionByEOS(loginBody);
            if (userSession.getUser() == null) {
                throw new WeChatNotBindException("EOS登录失败:" + loginBody.getNickName());
            }
            userVo = userSession.getUser();
            if(userVo.getRoleList()!=null){
                for(SysRole role : userVo.getRoleList()){
                    if(role.getRoleId()!=2){
                        throw new WeChatNotBindException("EOS登录失败:" + loginBody.getNickName()+"，该用户禁止EOS登录！");
                    }
                }
            }
        } else {
            userVo = userService.findUserByMobile(loginBody.getMobile());
            if (userVo == null) {
                throw new UsernameNotFoundException("手机号不存在:" + loginBody.getMobile());
            }
        }

        UserDetailsImpl userDetails = buildUserDeatils(userVo);

        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(mobileAuthenticationToken.getDetails());
        return authenticationToken;
    }

    private UserDetailsImpl buildUserDeatils(UserVO userVo) {
        return new UserDetailsImpl(userVo);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    /**
     * 根据微信信息查询 会话状态
     * https://mp.weixin.qq.com/debug/wxadoc/dev/api/api-login.html#wxloginobject
     * <p>
     * code 换取 session_key
     * ​这是一个 HTTPS 接口，开发者服务器使用登录凭证 code 获取 session_key 和 openid。
     * <p>
     * session_key 是对用户数据进行加密签名的密钥。为了自身应用安全，session_key 不应该在网络上传输。
     * <p>
     * 接口地址：
     * <p>
     * https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
     * 请求参数：
     * <p>
     * 参数	必填	说明
     * appid	是	小程序唯一标识
     * secret	是	小程序的 app secret
     * js_code	是	登录时获取的 code
     * grant_type	是	填写为 authorization_code
     *
     * @param channelKey
     */
    public WeChatSessionBody findWeChatSession(String channelKey, String wxAppId, String secret) {
        Map<String, String> params = new HashMap();
        //小程序ID
        params.put("appid", wxAppId);
        //小程序秘钥
        params.put("secret", secret);
        //用户传过来的
        params.put("js_code", channelKey);
        params.put("grant_type", "authorization_code");
        String json = HttpClientUtil.sendPost("https://api.weixin.qq.com/sns/jscode2session", params);

        if (StringUtils.isNotEmpty(json)) {
            //转换数据
            WeChatSessionBody weChatSession = JSON.parseObject(json, WeChatSessionBody.class);
            return weChatSession;
        } else {
            logger.error("微信接口查询超时channel key :" + channelKey);
        }
        return null;
    }

    /**
     * 查询用户的会话信息
     *
     * @param loginBody
     * @return SysUserSession
     */
    public SysUserSession findUserSessionByWeChat(MobileLoginBody loginBody) {
        ShopAppVo appVo = userService.findAppById(Integer.parseInt(loginBody.getAppId()));

        WeChatSessionBody weChatSession = findWeChatSession(loginBody.getChannel_key(), appVo.getWxAppId(), appVo.getAppSecret());
        if (StringUtils.isEmpty(weChatSession.getErrcode())) {
            //没有错误代表登录成功
            SysUserSession session = new SysUserSession();
            session.setChannel(MobileLoginBody.CHANNEL_WECHAT);
            session.setOpenId(weChatSession.getOpenid());
            SysUserSession userSession = userService.findUserSessionByOpenId(session);
            if (userSession == null) {
                userSession = session;
            }
            userSession.setUnionid(weChatSession.getUnionid());
            userSession.setOpenId(weChatSession.getOpenid());
            userSession.setSessionKey(weChatSession.getSession_key());
            userSession.setNickName(loginBody.getNickName());
            userSession.setAvatarUrl(loginBody.getAvatarUrl());
            userSession.setAppId(Integer.parseInt(loginBody.getAppId()));
            userSession.setGender(loginBody.getGender());

            userSession = userService.saveUserSession(userSession);
            if (userSession == null) {
                logger.debug("用户会话信息创建失败");
                return null;
            }
            return sessionUser(userSession);
        } else {
            logger.error("微信接口返回错误：" + weChatSession.getErrmsg());
        }
        return null;
    }


    public SysUserSession findUserSessionByEOS(MobileLoginBody loginBody) {
//        ShopAppVo appVo = userService.findAppById(Integer.parseInt(loginBody.getAppId()));
        //没有错误代表登录成功
        SysUserSession session = new SysUserSession();
        session.setChannel(MobileLoginBody.CHANNEL_EOS);
        session.setOpenId(loginBody.getNickName());
        SysUserSession userSession = userService.findUserSessionByOpenId(session);
        if (userSession == null) {
            userSession = session;
        }
        userSession.setUnionid("");
        userSession.setOpenId(loginBody.getNickName());
        userSession.setSessionKey("");
        userSession.setChannel(MobileLoginBody.CHANNEL_EOS);
        userSession.setNickName(loginBody.getNickName());
        userSession.setAvatarUrl(loginBody.getAvatarUrl());
        userSession.setAppId(Integer.parseInt(loginBody.getAppId()));
        userSession.setGender(loginBody.getGender());

        userSession = userService.saveUserSession(userSession);
        if (userSession == null) {
            logger.debug("用户会话信息创建失败");
            return null;
        }
        return sessionUser(userSession);
    }

    private SysUserSession sessionUser(SysUserSession userSession){
        if (userSession.getUserId() != null && userSession.getUserId() > 0) {
            UserVO user = userService.findUserByWxOpenId(userSession.getOpenId());
            user.setAppId(userSession.getAppId()+"");
            userSession.setUser(user);
            logger.debug("渠道登录成功");
        } else {
            logger.debug("未绑定系統帐号");
            UserVO userV = userService.createUserByUserSession(userSession);
            userV.setAppId(userSession.getAppId()+"");
            userSession.setUser(userV);
            logger.debug("渠道用户首次登录，创建一个新的用户信息。");
        }
        return userSession;
    }
}
