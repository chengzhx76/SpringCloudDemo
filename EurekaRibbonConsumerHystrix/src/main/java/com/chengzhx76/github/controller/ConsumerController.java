package com.chengzhx76.github.controller;

import com.chengzhx76.github.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/18
 */
@RestController
public class ConsumerController {

    @Autowired
    private HelloService service;

    @GetMapping("ribbon-consumer")
    public String helloConsumer() {
        return service.helloService();
    }

    @GetMapping("ribbon-time-out")
    public String testTimeOut() {
        return service.testTimeOut();
    }
}
