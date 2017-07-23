package com.chengzhx76.github.api.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/18
 */
@Service
public class HelloService {

    private final Logger _log = LoggerFactory.getLogger(HelloService.class);

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloFallback")
    public String helloService() {
        return restTemplate.getForEntity("http://eureka-client/hello", String.class).getBody();
    }

    @HystrixCommand(fallbackMethod = "timeOutFallback")
    public String testTimeOut() {
        long start = System.currentTimeMillis();
        String data = restTemplate.getForEntity("http://eureka-client/time-out", String.class).getBody();
        long end = System.currentTimeMillis();

        _log.info("Speed time: {}", end - start);

        return data;

    }

    public String helloFallback() {

        _log.warn("helloService -- time out");

        return "helloFallback";
    }

    public String timeOutFallback() {

        _log.warn("testTimeOut -- time out");

        return "timeOutFallback";
    }

}
