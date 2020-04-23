package com.lagou.sqlSession.impl;

import com.lagou.pojo.Configuration;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactrory;

public class DefultSqlSessionFactory implements SqlSessionFactrory {

    private Configuration configuration;

    public DefultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSqlSession() {
        return new DefultSqlSession(configuration);
    }
}
