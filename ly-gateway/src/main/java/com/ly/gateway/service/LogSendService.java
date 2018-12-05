package com.ly.gateway.service;

import com.netflix.zuul.context.RequestContext;

/**
 * @Author liyang
 * @Create 2018/6/16
 */
public interface LogSendService {
    /**
     * 往消息通道发消息
     *
     * @param requestContext requestContext
     */
    void send(RequestContext requestContext);
}
