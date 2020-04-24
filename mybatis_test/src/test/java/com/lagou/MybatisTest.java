package com.lagou;

import com.lagou.mapper.OrderMapper;
import com.lagou.mapper.UserMapper;
import com.lagou.pojo.Order;
import com.lagou.pojo.Role;
import com.lagou.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MybatisTest {
    @Test
    public void test() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.findAll();
        for (User user : users) {
            System.out.println(user);
        }
        User user = new User(1,null);
        User user1 = userMapper.findByCondition(user);
        System.out.println(user1);
    }

    @Test
    public void test1() throws IOException {//1对1查询
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        List<Order> orders = orderMapper.findAll();
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    @Test
    public void test2() throws IOException {//1对多查询
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> all = mapper.findAll();
        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void test3() throws IOException {//多对多查询
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> all = mapper.findAllUserAndRole();
        for (User user : all) {
            System.out.println(user.getName());
            List<Role> roleList = user.getRoleList();
            for (Role role : roleList) {
                System.out.println(role);
            }
            System.out.println("------------");
        }
    }
}
