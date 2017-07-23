package com.chengzhx76.gethub.controller;

import com.chengzhx76.gethub.client.RefactorHelloService;
import com.chengzhx76.github.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/19
 */
@RestController
public class RefactorConsumerController {

    @Autowired
    private RefactorHelloService service;

    @GetMapping("feign-consumer-2")
    public String feignConsumer2() {
        StringBuilder data = new StringBuilder();
        data.append(service.helloParam2("chengzhx76")).append(" -- ")
                .append(service.helloHeader2("chengzhx76", 26)).append(" -- ")
                .append(service.helloBody2(new User("chengzhx76", 26)));

        return data.toString();
    }

    @GetMapping("time-out-consumer-2")
    public String timeOutConsumer() {
        return service.timeOut2();
    }

}
