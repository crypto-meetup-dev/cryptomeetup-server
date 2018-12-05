package com.ly.admin.common.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.ly.common.bean.interceptor.DataScopeInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author liyang
 * @Create 2018/3/29
 */
@Configuration
@MapperScan({"com.ly.admin.mapper","com.ly.bt.mapper","com.ly.shop.mapper"})
public class MybatisPlusConfig {
    /**
     * 分页插件
     *
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 数据权限插件
     *
     * @return DataScopeInterceptor
     */
    @Bean
    public DataScopeInterceptor dataScopeInterceptor() {
        return new DataScopeInterceptor();
    }
}