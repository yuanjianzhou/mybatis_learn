package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.sqlSession.impl.DefultSqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactrory;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactroryBuilder {

    private Configuration configuration;

    public SqlSessionFactroryBuilder() {
        this.configuration = new Configuration();
    }

    public SqlSessionFactrory build(InputStream inputStream) throws PropertyVetoException, DocumentException, ClassNotFoundException {
        XmlConfigerBuilder xmlConfigerBuilder = new XmlConfigerBuilder(configuration);
        Configuration configuration = xmlConfigerBuilder.parseConfiguration(inputStream);
        SqlSessionFactrory sqlSessionFactory = new DefultSqlSessionFactory(configuration);
        return sqlSessionFactory;
    }
}
