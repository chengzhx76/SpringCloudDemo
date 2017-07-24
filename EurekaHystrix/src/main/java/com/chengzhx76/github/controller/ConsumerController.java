package com.chengzhx76.github.controller;

import com.chengzhx76.github.api.model.User;
import com.chengzhx76.github.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Desc:
 * Author: chengzhx76@qq.com
 * Date: 2017/7/20
 */
@RestController
public class ConsumerController {

    private final Logger _log = LoggerFactory.getLogger(ConsumerController.class);

    @Autowired
    private UserService service;

    @GetMapping("user")
    public User getUser() {
        return service.getUserById(1);
    }

    @GetMapping("user/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return service.getUserById(id);
    }

    @GetMapping("user-async/{id}")
    public User getUserByIdAsync(@PathVariable("id") int id) {
        Future<User> futureUser = service.getUserByIdAsync(id);
        User user = null;
        try {
            user = futureUser.get();
            //User user = futureUser.get(10L, TimeUnit.SECONDS);

            _log.info("---> {}", user);

        } catch (InterruptedException e) {
            _log.error("--> InterruptedException", e);
        } catch (ExecutionException e) {
            _log.error("--> ExecutionException", e);
        }/* catch (TimeoutException e) {
            _log.error("--> TimeoutException", e);
            ex = e;
        }*/

        return user;
    }

    @GetMapping("user-obs/{id}")
    public User getUserByIdObservable(@PathVariable("id") int id) {
        Observable<User> userObservable = service.getUserByIdObservable(id);

        final User[] user = {null};

        userObservable.subscribe(new Observer<User>() {
            @Override
            public void onCompleted() {
                // onNext/onError完成之后最后回调
                _log.info("---> execute onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                // 当产生异常时回调
                _log.error("---> onError", e);
            }
            @Override
            public void onNext(User _user) {
                // 获取结果后回调
                 user[0] = _user;
                _log.info("---> onNext {}", _user);
            }
        });

        return user[0] == null ? new User() : user[0];
    }

}
