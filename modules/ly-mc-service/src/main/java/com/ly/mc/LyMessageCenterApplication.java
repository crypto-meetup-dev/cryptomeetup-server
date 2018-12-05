package com.ly.mc;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author liyang
 * @Create 2018年01月15日13:24:23
 * 消息中心
 */
@EnableDiscoveryClient
@SpringBootApplication
public class LyMessageCenterApplication implements InitializingBean {

    public static void main(String[] args) {
        SpringApplication.run(LyMessageCenterApplication.class, args);
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
