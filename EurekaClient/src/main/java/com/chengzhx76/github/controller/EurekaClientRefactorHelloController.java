package com.chengzhx76.github.controller;

import com.chengzhx76.github.api.model.User;
import com.chengzhx76.github.api.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/19
 */
@RestController
public class EurekaClientRefactorHelloController implements HelloService {

    private final Logger _log = LoggerFactory.getLogger(EurekaClientRefactorHelloController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private Registration registration;

    @Override
    public String hello2() {
        String services = "Services: " + discoveryClient.getServices();
        String serverId = registration.getServiceId();

        _log.info("---> "+serverId);
        _log.info("---> "+services);

        return services;
    }

    @Override
    public String timeOut2() {
        int sleepTime = new Random().nextInt(5000);
        sleep(sleepTime);
        _log.info("---> ServiceId:{}, Sleep:{}", registration.getServiceId(), sleepTime);

        return sleepTime+"";
    }

    @Override
    public String helloParam2(String name) {
        return "Hello " + name;
    }

    @Override
    public User helloHeader2(String name, int age) {
        return new User(name, age);
    }

    @Override
    public String helloBody2(User user) {
        return "Hello " + user.getName() + ", " + user.getAge();
    }

    @Override
    public User helloModel2() {
        return new User("chengzhx76", 18, new Date());
    }


    private void sleep(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
