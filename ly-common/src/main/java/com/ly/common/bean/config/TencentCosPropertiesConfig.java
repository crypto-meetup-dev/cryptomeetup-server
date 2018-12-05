package com.ly.common.bean.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 腾讯配置
 *
 * @auther Administrator liyang
 * @Create 2018/9/7
 */
@Data
@Configuration
@ConditionalOnExpression("!'${tencent.cos}'.isEmpty()")
@ConfigurationProperties(prefix = "tencent.cos")
public class TencentCosPropertiesConfig {

    //秘钥
    private String secretId;

    //秘钥
    private String secretKey;

    //存储桶名称
    private String bucketName;

    //存储桶区域
    private String bucketAp;

    //商品文件根目录
    private String rootGoods;

    //域名信息
    private String domain;


    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");



    public String createImagePath(){

        String key = String.format("%s/%s/%s.jpg",
                rootGoods,
                dateFormat.format(new Date()),
                UUID.randomUUID().toString().replace("-", ""));

        return key;
    }


}
