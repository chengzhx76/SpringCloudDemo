package com.chengzhx76.github.hystrix;

import com.chengzhx76.github.api.model.User;
import com.chengzhx76.github.api.service.UserService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.List;

/**
 * Desc:
 * Author: 光灿
 * Date: 2017/7/23
 */
public class UserBatchCommand extends HystrixCommand<List<User>> {

    private List<Integer> ids;
    private UserService userService;

    protected UserBatchCommand(UserService userService, List<Integer> ids) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserGroupName")));
        this.ids = ids;
        this.userService = userService;
    }

    @Override
    protected List<User> run() throws Exception {
        return userService.getUserByIds(ids);
    }
}
