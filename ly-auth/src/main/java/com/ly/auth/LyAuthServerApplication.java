package com.ly.auth;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author liyang
 *         获取用户信息也是通过这个应用实现
 *         这里既是认证服务器，也是资源服务器
 *         EnableResourceServer
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.ly.auth", "com.ly.common.bean"})
public class LyAuthServerApplication implements InitializingBean {

    public static void main(String[] args) {
        SpringApplication.run(LyAuthServerApplication.class, args);
    }


    @Value("${spring.profiles.active}")
    private String active;

    @Override
    public void afterPropertiesSet(){
        System.out.print("---------------------------------------");
        System.out.print(active);
        System.out.println("---------------------------------------");
    }


}
