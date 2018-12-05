package com.ly.admin;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author liyang
 * @Create 2018-6-18
 */
@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.ly.admin", "com.ly.common.bean", "com.ly.bt"})
public class LyUpmsApplication implements InitializingBean {

    public static void main(String[] args) {
        SpringApplication.run(LyUpmsApplication.class, args);
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