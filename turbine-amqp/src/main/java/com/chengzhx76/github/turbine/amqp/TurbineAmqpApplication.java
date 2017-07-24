package com.chengzhx76.github.turbine.amqp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/24
 */
@EnableTurbineStream
@EnableDiscoveryClient
@SpringBootApplication
public class TurbineAmqpApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurbineAmqpApplication.class, args);
    }

}
