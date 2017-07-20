package com.chengzhx76.github.controller;

import com.chengzhx76.github.model.User;
import com.chengzhx76.github.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/20
 */
@RestController
public class ConsumerController {

    @Autowired
    private UserService service;

    @GetMapping("user/{id}")
    public User getUserById(@RequestParam("id") int id) {
        return service.getUserById(id);
    }
    @GetMapping("user-async/{id}")
    public User getUserByIdAsync(@RequestParam("id") int id) {
        return service.getUserByIdAsync(id);
    }

}
