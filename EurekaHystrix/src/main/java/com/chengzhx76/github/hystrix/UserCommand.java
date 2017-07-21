package com.chengzhx76.github.hystrix;

import com.chengzhx76.github.model.User;
import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * Desc: 读操作
 * Author: chengzhx76@qq.com
 * Date: 2017/7/20
 */
public class UserCommand extends HystrixCommand<User> {

    private final Logger _log = LoggerFactory.getLogger(UserCommand.class);

    private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("UserCommandKey");

    private int id;
    private RestTemplate restTemplate;

    public UserCommand(RestTemplate restTemplate, int id) {
//        super(HystrixCommandGroupKey.Factory.asKey("User"));

        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserGroupName"))
                .andCommandKey(GETTER_KEY)
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("UserThreadPoolKey")));

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

    @Override
    protected String getCacheKey() {
        // 根据id置入缓存
        return "_"+id;
    }

    public static void flushCache(String key) {
        HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear(key);
    }
}
