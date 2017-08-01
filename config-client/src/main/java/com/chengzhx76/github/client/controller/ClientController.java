package com.chengzhx76.github.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/25
 */
@RefreshScope
@RestController
public class ClientController {

    @Value("${from}")
    private String from;

    @Autowired
    private Environment env;

    @RequestMapping("/from")
    public String from() {
        return from;
    }

    @RequestMapping("/from-env")
    public String fromEnv() {
        return env.getProperty("from", "undefined");
    }

}
