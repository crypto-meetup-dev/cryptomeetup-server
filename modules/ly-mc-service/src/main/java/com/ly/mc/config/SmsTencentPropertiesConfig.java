package com.ly.mc.config;


import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 腾讯短信接口
 *
 * @auther Administrator liyang
 * @Create 2018/8/18 20:10
 */
@Data
@Configuration
@ConditionalOnExpression("!'${sms.tencent}'.isEmpty()")
@ConfigurationProperties(prefix = "sms.tencent")
public class SmsTencentPropertiesConfig {
    /**
     * 应用ID
     */
    private String accessKey;

    /**
     * 应用秘钥
     */
    private String secretKey;

    /**
     * 短信模板配置
     */
    private Map<String, String> channels;


    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Map<String, String> getChannels() {
        return channels;
    }

    public void setChannels(Map<String, String> channels) {
        this.channels = channels;
    }
}
