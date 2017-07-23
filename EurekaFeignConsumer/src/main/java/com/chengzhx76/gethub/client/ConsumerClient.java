package com.chengzhx76.gethub.client;

import com.chengzhx76.github.api.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/19
 */
//@FeignClient(name = "eureka-client", configuration = FullLogConfiguration.class)
@FeignClient("eureka-client")
public interface ConsumerClient {

    @GetMapping("hello")
    String hello();

    @GetMapping("time-out")
    String timeOut();

    @GetMapping("hello-param")
    String helloParam(@RequestParam("name") String name);

    @GetMapping("hello-header")
    User helloHeader(@RequestHeader("name") String name, @RequestHeader("age") int age);

    @PostMapping("hello-body")
    String helloBody(@RequestBody User user);

    @GetMapping("hello-model")
    User helloModel();

}
