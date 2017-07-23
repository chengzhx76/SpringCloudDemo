package com.chengzhx76.github.hystrix;

import com.chengzhx76.github.api.model.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * Desc: 写操作，清除缓存
 * Author: chengzhx76@qq.com
 * Date: 2017/7/20
 */
public class UserPostCommand extends HystrixCommand<User> {

    private final Logger _log = LoggerFactory.getLogger(UserPostCommand.class);

    private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("UserCommandKey");

    private User user;
    private RestTemplate restTemplate;

    public UserPostCommand(RestTemplate restTemplate, User user) {
//        super(HystrixCommandGroupKey.Factory.asKey("User"));

        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserGroupName"))
                .andCommandKey(GETTER_KEY)
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("UserThreadPoolKey")));

        this.user = user;
        this.restTemplate = restTemplate;
    }

    @Override
    protected User run() throws Exception {
        // 写操作
        User _user = restTemplate.postForObject("http://eureka-client/user", user, User.class);
        // 刷新缓存，清楚缓存中失效的User
        UserCommand.flushCache("_"+user.getId());
        return _user;
    }

    @Override
    protected User getFallback() {
        _log.warn("---> 进入Fallback");
        return new User(0, "未知", 18, new Date());
    }

}
