package com.chengzhx76.github;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/18
 */
@SpringCloudApplication
public class EurekaRibbonConsumerHystrixApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaRibbonConsumerHystrixApplication.class, args);
    }

}
