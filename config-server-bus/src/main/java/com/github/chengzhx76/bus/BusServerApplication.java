package com.github.chengzhx76.bus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/8/4
 */
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class BusServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusServerApplication.class, args);
    }

}
