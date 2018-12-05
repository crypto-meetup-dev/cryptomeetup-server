package com.ly.auth.feign;

import com.ly.auth.feign.fallback.UserServiceFallbackImpl;
import com.ly.common.entity.SysUserSession;
import com.ly.common.vo.ShopAppVo;
import com.ly.common.vo.UserVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * @Author liyang
 * @Create 2018/7/31
 */
@FeignClient(name = "ly-upms-service", fallback = UserServiceFallbackImpl.class)
public interface UserService {
    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @return UserVo
     */
    @GetMapping("/user/findUserByUsername/{username}")
    UserVO findUserByUsername(@PathVariable("username") String username);

    @GetMapping("/user/findUserByUsername/{username}")
    UserVO findUserByUsername(@PathVariable("username") String username, @RequestHeader("appId") String appId);

    /**
     * 通过手机号查询用户、角色信息
     *
     * @param mobile 手机号
     * @return UserVo
     */
    @GetMapping("/user/findUserByMobile/{mobile}")
    UserVO findUserByMobile(@PathVariable("mobile") String mobile);

    /**
     * 根据OpenId查询用户信息
     * @param openId openId
     * @return UserVo
     */
    @GetMapping("/user/findUserByOpenId/{openId}")
    UserVO findUserByOpenId(@PathVariable("openId") String openId);


    /**
     *
     * @param session
     * @return
     */
    @PostMapping("/user/findUserSessionByOpenId")
    SysUserSession findUserSessionByOpenId(SysUserSession session);

    /**
     *
     * @param userSession
     */
    @PutMapping("/user/saveUserSession")
    SysUserSession saveUserSession(SysUserSession userSession);

    /**
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/findUserId/{userId}")
    UserVO findUserById(@PathVariable("userId")Integer userId);


    /**
     * 根据微信用户的openId查询用户
     * @param wxOpenId
     * @return
     */
    @GetMapping("/user/findUserByWxOpenId/{wxOpenId}")
    UserVO findUserByWxOpenId(@PathVariable("wxOpenId")String wxOpenId);


    /**
     * 根据用户会话信息创建一个用户
     * @param userSession
     * @return
     */
    @PutMapping("/user/createUserByUserSession")
    UserVO createUserByUserSession(SysUserSession userSession);


    /**
     * 获取商户信息
     * @param appId
     * @return
     */
    @GetMapping("/shop/admin/app/findById/{appId}")
    ShopAppVo findAppById(@PathVariable("appId")Integer appId);

}
