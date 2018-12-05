package com.ly.mc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author liyang
 * @Create 2018/4/2
 * 钉钉服务配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sms.dingtalk")
public class DingTalkPropertiesConfig {
    /**
     * webhook
     */
    private String webhook;

}
