package com.chengzhx76.github.service;

import com.chengzhx76.github.api.model.User;
import com.chengzhx76.github.exception.BadRequestException;
import com.chengzhx76.github.hystrix.UserCollapseCommand;
import com.chengzhx76.github.hystrix.UserCommand;
import com.chengzhx76.github.hystrix.UserPostCommand;
import com.chengzhx76.github.service.impl.UserServiceImpl;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
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

    // 同步执行 --start
    @HystrixCommand
    public User getUserById(int id) {
        // 原生写法
        User user = new UserCommand(restTemplate, id).execute();
        //User user = restTemplate.getForObject("http://eureka-client/user/{1}", User.class, id);
        return user;
    }

    // 异步执行 --start
    @HystrixCommand
    public Future<User> getUserByIdAsync(final int id) {
        // 原生写法
        //Future<User> futureUser = new UserCommand(restTemplate, id).queue();
        Future<User> futureUser = new AsyncResult<User>() {
            @Override
            public User invoke() {
                return restTemplate.getForObject("http://eureka-client/user/{1}", User.class, id);
            }
        };
        return futureUser;
    }

    // 使用observe方式执行 --start
    //@HystrixCommand(observableExecutionMode = ObservableExecutionMode.EAGER)
    // 使用toObservable方式执行
    @HystrixCommand(observableExecutionMode = ObservableExecutionMode.LAZY)
    public Observable<User> getUserByIdObservable(final int id) {
        // 原生写法
        //Observable<User> userObser = new UserObservableCommand(id, restTemplate).observe();
        //Observable<User> userObservable = new UserObservableCommand(id, restTemplate).toObservable();

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

    // 服务降级 --start
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

    // 异常封装 --start
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

    // 命令名称、分组、线程池 --start
    @HystrixCommand(commandKey = "getUserByIdCommand", groupKey = "UserGroup", threadPoolKey = "getUserByIdCommandThread")
    public User getUserByIdCommand(int id) {
        return restTemplate.getForObject("http://eureka-client/user/{1}", User.class, id);
    }

    // 缓存 --start
    // 原生写法 --@getCacheKey()
    public User getUserByIdCache(int id) {
        return new UserCommand(restTemplate, id).execute();
    }

    // 原生写法 --@flushCache()
    public User updateRemoveCache(@CacheKey("id") User user) {
        User _user = new UserPostCommand(restTemplate, user).execute();
        return _user;
    }

    //@CacheResult // 使用参数做为Key
    @CacheResult(cacheKeyMethod = "getUserByIdCacheKey")
    @HystrixCommand
    public User getUserByIdCache2(int id) {
        return restTemplate.getForObject("http://eureka-client/user/{1}", User.class, id);
    }

    public String getUserByIdCacheKey(int id) {
        return "_" + id;
    }

    @CacheResult
    @HystrixCommand
    public User getUserByIdCache3(@CacheKey("id") int id) {
        return restTemplate.getForObject("http://eureka-client/user/{1}", User.class, id);
    }

    @CacheResult
    @HystrixCommand
    public User getUserByIdCache4(@CacheKey("id") User user) {
        return restTemplate.getForObject("http://eureka-client/user/{1}", User.class, user.getId());
    }

    @CacheRemove(commandKey = "getUserByIdCache")
    @HystrixCommand
    public void updateRemoveCache2(@CacheKey("id") User user) {
        restTemplate.postForEntity("http://eureka-client/user", user, User.class);
    }

    // 请求合并 --start
    @Autowired
    private UserServiceImpl service;

    // 原生写法
    public User getUserByIdMergeRequest(int id) throws ExecutionException, InterruptedException {
        Future<User> futureUser = new UserCollapseCommand(service, id).queue();
        return futureUser.get();
    }

    @HystrixCollapser(batchMethod = "getUserByIdsMergeRequest", collapserProperties = {
            @HystrixProperty(name = "timeDelayInMilliseconds", value = "100")
    })
    public User getUserByIdMergeRequest2(int id) {
        return null;
    }

    @HystrixCommand
    public List<User> getUserByIdsMergeRequest(List<Integer> ids) {
        return restTemplate.getForObject("http://eureka-client/users?ids={1}", List.class, StringUtils.join(ids, ","));
    }

}
