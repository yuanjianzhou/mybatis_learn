package com.lagou.sqlSession.impl;

import com.lagou.config.BoundSql;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.sqlSession.Excutor;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExcutor implements Excutor {
    private Connection connection = null;
    public <E> List<E> qurey(Configuration configuration, MappedStatement mappedStatement, Object[] params) throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException {
        connection = configuration.getDataSource().getConnection();
        String sql = mappedStatement.getSql();
        Class<?> parameterType = mappedStatement.getParameterType();
        Class<?> resultType = mappedStatement.getResultType();
        //对sql进行处理  #{} or ${}进行替换 成 ?
        BoundSql boundSql = getBoundSql(sql);
        String sqlText = boundSql.getSqlText();
        //获取预编译PreparedStatement对象,
        // 设置参数preparedStatement.setint(1,2);设置第一个占位符为2；防止sql注入；执行10次，只需要传1次sql到数据库，减小传输量，数据库不需要在编译，提升性能
        PreparedStatement preparedStatement = connection.prepareStatement(sqlText);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String name = parameterMapping.getContent();//获取#{id} id
            Field declaredField = parameterType.getDeclaredField(name);
            declaredField.setAccessible(true);//关闭安全检查就可以达到提升反射速度
            Object o = declaredField.get(params[0]);//从传入的参数（user对象）中获取id的值
            preparedStatement.setObject(i+1,o);//给占位符赋值
        }
        ResultSet resultSet = preparedStatement.executeQuery();//进行查询了
        ArrayList<E> results = new ArrayList<E>();
        while (resultSet.next()){
            ResultSetMetaData metaData = resultSet.getMetaData();//得到源数据
            E e = (E) resultType.newInstance();//根据resultType获取新的对象
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);//属性名id,name
                Object value = resultSet.getObject(columnName);//属性值value
                //创建属性描述器。为属性生成读写方法
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultType);
                Method writeMethod = propertyDescriptor.getWriteMethod();//获取写方法
                writeMethod.invoke(e,value);//将查询出来的值写到需要返回的对象中
            }
            results.add(e);
        }
        return results;
    }

    private BoundSql getBoundSql(String sql) {
        //标记处理类：主要是配合通用标记解析器GenericTokenParser类完成对配置文件等的解析工作。其中TokenHandler主要完成处理
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{","}",parameterMappingTokenHandler);
        String sqlText = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(sqlText,parameterMappings);
        return boundSql;
    }
}
