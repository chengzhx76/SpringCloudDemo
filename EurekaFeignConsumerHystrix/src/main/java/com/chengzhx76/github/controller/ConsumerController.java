package com.chengzhx76.github.controller;

import com.chengzhx76.github.client.ConsumerClient;
import com.chengzhx76.github.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/19
 */
@RestController
public class ConsumerController {

    @Autowired
    private ConsumerClient client;

    @GetMapping("hello-consumer")
    public String helloConsumer() {
        return client.hello();
    }

    @GetMapping("time-out-consumer")
    public String timeOutConsumer() {
        return client.timeOut();
    }

    @GetMapping("feign-consumer")
    public String feignConsumer() {
        StringBuilder data = new StringBuilder();
        data.append(client.helloParam("chengzhx76")).append(" -- ")
                .append(client.helloHeader("chengzhx76", 26)).append(" -- ")
                .append(client.helloBody(new User("chengzhx76", 26)));

        return data.toString();
    }

    @GetMapping("model-consumer")
    public User modelConsumer() {
        return client.helloModel();
    }

}
