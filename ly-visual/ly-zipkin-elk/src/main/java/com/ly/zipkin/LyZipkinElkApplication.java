package com.ly.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin.server.EnableZipkinServer;

/**
 * @Author liyang
 * @Create 2018-6-29
 * zipkin 链路追踪
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableZipkinServer
public class LyZipkinElkApplication {

    public static void main(String[] args) {
        SpringApplication.run(LyZipkinElkApplication.class, args);
    }
}
