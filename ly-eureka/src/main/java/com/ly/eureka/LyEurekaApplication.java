package com.ly.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author liyang
 */
@EnableEurekaServer
@SpringBootApplication
public class LyEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LyEurekaApplication.class, args);
	}
}
