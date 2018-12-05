package com.ly.auth.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * 微信未绑定帐号
 * @auther Administrator liyang
 * @Create 2018/8/18
 */
public class WeChatNotBindException extends AuthenticationException {
    public WeChatNotBindException(String msg) {
        super(msg);
    }

    public WeChatNotBindException(String msg, Throwable t) {
        super(msg, t);
    }
}
