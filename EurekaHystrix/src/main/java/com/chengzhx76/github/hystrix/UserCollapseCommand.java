package com.chengzhx76.github.hystrix;

import com.chengzhx76.github.api.model.User;
import com.chengzhx76.github.api.service.UserService;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Desc:
 * Author: 光灿
 * Date: 2017/7/23
 */
public class UserCollapseCommand extends HystrixCollapser<List<User>, User, Integer> {

    private UserService userService;
    private int userId;

    public UserCollapseCommand(UserService userService, int userId) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("userCollapseCommand"))
        .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
        this.userService = userService;
        this.userId = userId;
    }

    @Override
    public Integer getRequestArgument() {
        return userId;
    }

    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Integer>> collapsedRequests) {
        List<Integer> ids = new ArrayList<>(collapsedRequests.size());
        ids.addAll(collapsedRequests.stream().map(CollapsedRequest :: getArgument).collect(Collectors.toList()));
        return new UserBatchCommand(userService, ids);
    }

    @Override
    protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, Integer>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<User, Integer> collapsedRequest : collapsedRequests) {
            User user = batchResponse.get(count++);
            collapsedRequest.setResponse(user);
        }
    }
}
