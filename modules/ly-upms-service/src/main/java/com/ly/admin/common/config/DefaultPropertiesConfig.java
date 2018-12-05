package com.ly.admin.common.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 一些默认的参数配置
 * @Author liyang
 * @Create 2018/8/22
 */
@Data
@Configuration
@ConditionalOnExpression("!'${default}'.isEmpty()")
@ConfigurationProperties(prefix = "default")
public class DefaultPropertiesConfig {

    /**
     * 默认的角色ID
     */
    private List<Integer> userNormalRoles;


    /**
     * 默认的部门ID
     */
    private Integer deptId;

}
