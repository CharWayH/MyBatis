package com.lagou.test;

import com.lagou.mapper.IOrderMapper;
import com.lagou.mapper.IUserMapper;
import com.lagou.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;


public class CacheTest {


    private IUserMapper iUserMapper = null;

    private IOrderMapper iOrderMapper = null;


    private SqlSession sqlSession = null;

    private SqlSessionFactory sqlSessionFactory = null;

    @Before
    public void before() throws IOException {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = sqlSessionFactory.openSession(true);
        iUserMapper = sqlSession.getMapper(IUserMapper.class);
        iOrderMapper = sqlSession.getMapper(IOrderMapper.class);
    }


    @Test
    public void testFirstCache(){
        // 第一次查询
        User user1 = iUserMapper.findUserById(1);

        // 第二次查询
        User user2 = iUserMapper.findUserById(1);

        // 判断两次查询地址值是否相等
        System.out.println(user1 == user2);
        System.out.println(user1);

    }


    @Test
    public void testSecondCache(){
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        SqlSession sqlSession3 = sqlSessionFactory.openSession();

        IUserMapper mapper1 = sqlSession1.getMapper(IUserMapper.class);
        IUserMapper mapper2 = sqlSession2.getMapper(IUserMapper.class);
        IUserMapper mapper3 = sqlSession3.getMapper(IUserMapper.class);

        User user1 = mapper1.findUserById(1);
        // 清空一级缓存
        sqlSession1.close();

        User user2 = mapper2.findUserById(1);
        System.out.println(user1 == user2);
    }
}
