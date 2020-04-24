package com.lagou.mapper;

import com.lagou.pojo.User;

import java.util.List;

public interface UserMapper {
    public List<User> findAll();
    public User findByCondition(User user);
    public List<User> findAllUserAndRole();
}
