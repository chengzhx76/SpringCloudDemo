package com.chengzhx76.github.hystrix;

import com.chengzhx76.github.model.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/20
 */
public class UserCommand extends HystrixCommand<User> {

    private final Logger _log = LoggerFactory.getLogger(UserCommand.class);

    private int id;
    private RestTemplate restTemplate;

    public UserCommand(RestTemplate restTemplate, int id) {
        super(HystrixCommandGroupKey.Factory.asKey("User"));
        this.id = id;
        this.restTemplate = restTemplate;
    }

    @Override
    protected User run() throws Exception {
        return restTemplate.getForObject("http://eureka-client/user/{1}", User.class, id);
    }

    @Override
    protected User getFallback() {
        _log.warn("---> 进入Fallback");
        return new User(0, "未知", 18, new Date());
    }
}
