package com.ly.daemon;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author liyang
 * @Create 2018/2/7
 * 分布式任务调度模块
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
//@ComponentScan(basePackages = {"com.ly.daemon", "com.ly.common.bean"})
public class LyDaemonApplication implements InitializingBean {

    public static void main(String[] args) {
        SpringApplication.run(LyDaemonApplication.class, args);
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
