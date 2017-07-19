package com.chengzhx76.github.service;

import com.chengzhx76.github.model.User;
import org.springframework.web.bind.annotation.*;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/19
 */
@RequestMapping("/refactor")
public interface HelloService {

    @GetMapping("hello-2")
    String hello2();

    @GetMapping("time-out-2")
    String timeOut2();

    @GetMapping("hello-param-2")
    String helloParam2(@RequestParam("name") String name);

    @GetMapping("hello-header-2")
    User helloHeader2(@RequestHeader("name") String name, @RequestHeader("age") int age);

    @PostMapping("hello-body-2")
    String helloBody2(@RequestBody User user);

    @GetMapping("hello-model-2")
    User helloModel2();

}
