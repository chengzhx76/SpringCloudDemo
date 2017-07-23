package com.chengzhx76.github.fallback;

import com.chengzhx76.github.client.ConsumerClient;
import com.chengzhx76.github.api.model.User;
import org.springframework.stereotype.Component;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/20
 */
@Component
public class HelloServiceFallback implements ConsumerClient {
    @Override
    public String hello() {
        return "error";
    }

    @Override
    public String timeOut() {
        return "error";
    }

    @Override
    public String helloParam(String name) {
        return "error";
    }

    @Override
    public User helloHeader(String name, int age) {
        return new User("error", 0);
    }

    @Override
    public String helloBody(User user) {
        return "error";
    }

    @Override
    public User helloModel() {
        return new User("error", 0);
    }
}
