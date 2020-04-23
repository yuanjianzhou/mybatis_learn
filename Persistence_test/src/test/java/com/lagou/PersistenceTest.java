package com.lagou;

import com.lagou.dao.UserDao;
import com.lagou.pojo.User;
import com.lagou.io.Resources;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactrory;
import com.lagou.config.SqlSessionFactroryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class PersistenceTest {

    @Test
    public void test() throws Exception {
        InputStream in = Resources.getResourceAsInputeStream("sqlMapConfig.xml");
        SqlSessionFactrory sqlSessionFactrory = new SqlSessionFactroryBuilder().build(in);
        SqlSession sqlSession = sqlSessionFactrory.openSqlSession();
        UserDao userMapper = sqlSession.getMapper(UserDao.class);
        List<User> users = userMapper.findAll();
        for (User user : users) {
            System.out.println(user);
        }
        User user = new User();
        user.setId(1);
        user.setName("zhangsan");
        User user1 = userMapper.findByCondition(user);
        System.out.println(user1);
    }
}
