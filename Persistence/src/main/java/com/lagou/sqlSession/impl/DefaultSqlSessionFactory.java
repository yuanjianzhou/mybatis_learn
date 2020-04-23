package com.lagou.sqlSession.impl;

import com.lagou.pojo.Configuration;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactrory;

public class DefaultSqlSessionFactory implements SqlSessionFactrory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSqlSession() {
        return new DefaultSqlSession(configuration);
    }
}
