package com.ly.admin.common.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 拼多多配置信息
 *
 * @auther Administrator liyang
 * @create 2018/11/8 23:33
 */
@Data
@Configuration
@ConditionalOnExpression("!'${pdd}'.isEmpty()")
@ConfigurationProperties(prefix = "pdd")
public class PddPropertiesConfig {

    //商户号
    private String clientId;

    //密码
    private String clientSecret;


}
