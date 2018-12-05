package com.ly.auth.config;

import lombok.Data;

/**
 * @Author liyang
 * @Create 2018/2/28
 * social 登录基础配置
 */
@Data
public class SocialPropertiesConfig {
    /**
     * 提供商
     */
    private String providerId;
    /**
     * 应用ID
     */
    private String clientId;
    /**
     * 应用密钥
     */
    private String clientSecret;

}
