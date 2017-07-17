package com.chengzhx76.github;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/17
 */
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
        new SpringApplicationBuilder(EurekaClientApplication.class).web(true).run(args);
    }
}
