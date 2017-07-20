package com.chengzhx76.github.hystrix;

import com.chengzhx76.github.model.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/20
 */
public class UserCommand extends HystrixCommand<User> {

    private int id;
    private RestTemplate restTemplate;

    public UserCommand(RestTemplate restTemplate, int id) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.id = id;
        this.restTemplate = restTemplate;
    }

    @Override
    protected User run() throws Exception {
        return restTemplate.getForObject("http://eureka-client/user/{1}", User.class, id);
    }
}
