package com.chengzhx76.github.service;

import com.chengzhx76.github.exception.BadRequestException;
import com.chengzhx76.github.hystrix.UserCommand;
import com.chengzhx76.github.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.util.Date;
import java.util.concurrent.Future;

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
        //User user = restTemplate.getForObject("http://eureka-client/user/{1}", User.class, id);
        return user;
    }

    // 异步执行
    @HystrixCommand
    public Future<User> getUserByIdAsync(final int id) {
        //Future<User> futureUser = new UserCommand(restTemplate, id).queue();
        Future<User> futureUser = new AsyncResult<User>() {
            @Override
            public User invoke() {
                return restTemplate.getForObject("http://eureka-client/user/{1}", User.class, id);
            }
        };
        return futureUser;
    }

    // 使用observe方式执行
    //@HystrixCommand(observableExecutionMode = ObservableExecutionMode.EAGER)
    // 使用toObservable方式执行
    @HystrixCommand(observableExecutionMode = ObservableExecutionMode.LAZY)
    public Observable<User> getUserByIdObservable(int id) {

//        Observable<User> userObser = new UserObservableCommand(id, restTemplate).observe();
//        Observable<User> userObservable = new UserObservableCommand(id, restTemplate).toObservable();

        Observable<User> userObservable = Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        User user = restTemplate.getForObject("http://eureka-client/user/{1}", User.class, id);
                        subscriber.onNext(user);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

        return userObservable;
    }

    @HystrixCommand(fallbackMethod = "defaultUser")
    public User getUserByIdFallback(int id) {
        return restTemplate.getForObject("http://eureka-client/user/{1}", User.class, id);
    }

    @HystrixCommand(fallbackMethod = "defaultUserSec")
    public User defaultUser() {
        return new User(0, "First Fallback", 18, new Date());
    }

    public User defaultUserSec() {
        return new User(-1, "Second Fallback", 18, new Date());
    }

    @HystrixCommand(ignoreExceptions = {BadRequestException.class})
    public User getUserByIdException(int id) {
        return restTemplate.getForObject("http://eureka-client/user/{1}", User.class, id);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public User getUserByIdThrowable(int id) {
        throw new RuntimeException("getUserBtId command failed");
    }

    private User fallback(int id, Throwable e) {
        _log.warn("---> ID: {}, msg: {}", id, e.getMessage());
        return new User(0, "First Fallback", 18, new Date());
    }

}
