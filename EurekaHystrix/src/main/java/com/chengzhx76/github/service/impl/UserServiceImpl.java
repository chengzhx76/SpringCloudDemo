package com.chengzhx76.github.service.impl;

import com.chengzhx76.github.api.model.User;
import com.chengzhx76.github.api.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Desc:
 * Author: 光灿
 * Date: 2017/7/23
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User getUserById(int id) {
        return restTemplate.getForObject("http://eureka-client/user/{1}", User.class, id);
    }

    @Override
    public List<User> getUserByIds(List<Integer> ids) {
        return restTemplate.getForObject("http://eureka-client/users?ids={1}", List.class, StringUtils.join(ids, ","));
    }
}
