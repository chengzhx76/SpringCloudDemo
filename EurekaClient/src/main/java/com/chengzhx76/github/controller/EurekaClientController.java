package com.chengzhx76.github.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index() {
        String services = "Services: " + client.getServices();

        String serverId = registration.getServiceId();

        _log.info("---> "+serverId);
        _log.info("---> "+services);

        return services;
    }

}
