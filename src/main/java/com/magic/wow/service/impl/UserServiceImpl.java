package com.magic.wow.service.impl;

import com.magic.wow.mapper.UserMapper;
import com.magic.wow.model.DTRequest;
import com.magic.wow.model.User;
import com.magic.wow.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * app
 * Created by zhaoxf on 2017/3/6.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findByName(String name, String pwd) {
        return userMapper.findByName(name, pwd);
    }

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public long countByQuery(DTRequest dtRequest) {
        return userMapper.countByQuery(dtRequest);
    }

    @Override
    public List<User> findByQuery(DTRequest dtRequest) {
        return userMapper.pageByQuery(dtRequest);
    }

    @Override
    public int del(int id) {
        return userMapper.del(id);
    }

}
