package com.chengzhx76.github.gateway;

import com.chengzhx76.github.gateway.filter.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/25
 */
@EnableZuulProxy
@SpringCloudApplication
public class GatewayApplication {

    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
