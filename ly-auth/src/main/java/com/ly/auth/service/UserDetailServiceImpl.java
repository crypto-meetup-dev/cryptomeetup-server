package com.ly.auth.service;

import com.ly.auth.feign.UserService;
import com.ly.auth.util.UserDetailsImpl;
import com.ly.common.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author liyang
 * @Create 2018/6/26
 * <p>
 */
@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO userVo = userService.findUserByUsername(username);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserDetailsImpl userDetails = new UserDetailsImpl(userVo);
        if(StringUtils.isEmpty(userDetails.getAppId())){
            userDetails.setAppId(request.getHeader("appId"));
        }
        return userDetails;
    }
}
