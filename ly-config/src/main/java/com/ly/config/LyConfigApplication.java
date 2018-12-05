package com.ly.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @Author liyang
 */
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class LyConfigApplication implements InitializingBean {


    public static void main(String[] args) {
        SpringApplication.run(LyConfigApplication.class, args);
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
