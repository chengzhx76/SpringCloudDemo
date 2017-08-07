package com.github.chengzhx76.bus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/25
 */
@EnableDiscoveryClient
@SpringBootApplication
public class BusClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusClientApplication.class, args);
    }

}
