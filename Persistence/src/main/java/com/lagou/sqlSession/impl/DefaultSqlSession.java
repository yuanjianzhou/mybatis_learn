package com.lagou.sqlSession.impl;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.sqlSession.SqlSession;

import java.lang.reflect.*;
import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    private SimpleExcutor sampleExcutor = new SimpleExcutor();
    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    public <E> List<E> selectList(String statementId, Object... params) throws Exception{
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> objects = sampleExcutor.qurey(configuration, mappedStatement, params);
        return (List<E>) objects;
    }

    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if(objects.size() == 1){
            return (T) objects.get(0);
        }else{
            throw new Exception("返回结果没有，或者过多");
        }
    }

    public <T> T getMapper(Class<?> mapperClass) {
        T t = (T) Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();//selectOne selectList
                String className = method.getDeclaringClass().getName();//namespace
                String key = className + "." + methodName;//statementId
                Type genericReturnType = method.getGenericReturnType();//获取调用方法返回值类型，用于判断是否存在泛型
                if(genericReturnType instanceof ParameterizedType){//判断是否实现泛型类型参数化
                    return selectList(key,args);
                }else{
                    return selectOne(key,args);
                }
            }
        });
        return t;
    }
}
