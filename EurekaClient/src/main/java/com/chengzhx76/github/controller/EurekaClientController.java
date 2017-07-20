package com.chengzhx76.github.controller;

import com.chengzhx76.github.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Random;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/17
 */
@RestController
public class EurekaClientController {

    private final Logger _log = LoggerFactory.getLogger(EurekaClientController.class);

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private Registration registration;

    @RequestMapping("/hello")
    public String index() {
        String services = "Services: " + client.getServices();
        String serverId = registration.getServiceId();

        _log.info("---> "+serverId);
        _log.info("---> "+services);

        return services;
    }

    @GetMapping("time-out")
    public String testTimeOut() throws InterruptedException {
        int sleepTime = new Random().nextInt(4000);
        Thread.sleep(sleepTime);
        _log.info("---> ServiceId:{}, Sleep:{}", registration.getServiceId(), sleepTime);

        return sleepTime+"";
    }

    @GetMapping("hello-param")
    public String helloParam(@RequestParam String name) {
        return "Hello " + name;
    }

    @GetMapping("hello-header")
    public User helloHeader(@RequestHeader String name, @RequestHeader int age) {
        return new User(name, age);
    }

    @GetMapping("hello-model")
    public User helloModel() {
        return new User("chengzhx76", 18, new Date());
    }

    @PostMapping("hello-body")
    public String helloBody(@RequestBody User user) {
        return "Hello " + user.getName() + ", " + user.getAge();
    }

}
