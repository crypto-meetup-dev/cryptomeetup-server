package com.ly.common.bean.config;


import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author liyang
 * @Create 2018/12/9
 */
@Data
@Configuration
@ConditionalOnExpression("!'${git.portal.pull}'.isEmpty()")
@ConfigurationProperties(prefix = "git.portal.pull")
public class GitPortalPullPropertiesConfig {


    private String url;


    private String branch;


    private String localPath;


    private String objectPath;

}
