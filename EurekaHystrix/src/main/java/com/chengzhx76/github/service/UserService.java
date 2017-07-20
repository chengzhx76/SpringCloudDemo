package com.chengzhx76.github.service;

import com.chengzhx76.github.hystrix.UserCommand;
import com.chengzhx76.github.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/20
 */
@Service
public class UserService {

    private final Logger _log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private RestTemplate restTemplate;

    // 同步执行
    @HystrixCommand
    public User getUserById(int id) {

        User user = new UserCommand(restTemplate, id).execute();
        User user1 = restTemplate.getForObject("http://eureka-client/user/{1}", User.class, id);

        return user;
    }

    // 异步执行
    @HystrixCommand
    public User getUserByIdAsync(final int id) {
        Future<User> futureUser = new UserCommand(restTemplate, id).queue();

        Future<User> futureUser1 = new AsyncResult<User>() {
            @Override
            public User invoke() {
                return restTemplate.getForObject("http://eureka-client/user/{1}", User.class, id);
            }
        };

        try {
            return futureUser.get(1000L, TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

}
