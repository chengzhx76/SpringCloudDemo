package com.chengzhx76.github.api.service;

import com.chengzhx76.github.api.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Desc:
 * Author: 光灿
 * Date: 2017/7/23
 */
@RestController("request")
public interface UserService {

    @GetMapping("getUserById")
    User getUserById(int id);

    @GetMapping("getUserByIds")
    List<User> getUserByIds(List<Integer> ids);

}
