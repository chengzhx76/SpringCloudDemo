package com.chengzhx76.github;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/19
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaFeignConsumerHystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaFeignConsumerHystrixApplication.class, args);
    }

}
