package com.magic.wow.service;

import com.magic.wow.model.DTRequest;
import com.magic.wow.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoxf on 2017/3/6.
 */
public interface UserService {
    User findByName(String name,String pwd);
    void addUser(User user);
    long countByQuery(DTRequest dtRequest);
    List<User> findByQuery(DTRequest dtRequest);
    int del(int id);

}
