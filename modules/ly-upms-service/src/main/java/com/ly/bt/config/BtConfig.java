package com.ly.bt.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @auther Administrator liyang
 * @Create 2018/10/9 21:28
 */
@Configuration
public class BtConfig {

//    @Autowired
//    PddTraceInterceptor pddTraceInterceptor;


    @Bean
    public RestTemplate pddRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setInterceptors(pddTraceInterceptor);
        return restTemplate;
    }

}
