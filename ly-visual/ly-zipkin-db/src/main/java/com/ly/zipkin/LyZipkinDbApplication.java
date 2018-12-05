package com.ly.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin.server.EnableZipkinServer;

/**
 * @Author liyang
 * @Create 2018-04-24
 * zipkin mysql 存储实现
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableZipkinServer
public class LyZipkinDbApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyZipkinDbApplication.class, args);
    }
}
