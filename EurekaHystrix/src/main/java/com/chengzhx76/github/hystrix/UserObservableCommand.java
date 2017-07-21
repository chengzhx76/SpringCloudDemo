package com.chengzhx76.github.hystrix;

import com.chengzhx76.github.model.User;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.util.Date;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/21
 */
public class UserObservableCommand extends HystrixObservableCommand<User> {

    private final Logger _log = LoggerFactory.getLogger(UserObservableCommand.class);

    private int id;
    private RestTemplate template;

    public UserObservableCommand(int id, RestTemplate template) {
        super(HystrixCommandGroupKey.Factory.asKey("UserObservable"));
        this.id = id;
        this.template = template;
    }

    @Override
    protected Observable<User> construct() {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        User user = template.getForObject("http://eureka-client/user/{1}", User.class, id);
                        subscriber.onNext(user);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    protected Observable<User> resumeWithFallback() {
        _log.warn("---> 进入Fallback");
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(new User(0, "未知", 18, new Date()));
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
