package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

public interface UserDao {
    public List<User> findAll();
    public User findByCondition(User user);
}
