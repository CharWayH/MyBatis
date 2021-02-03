package com.lagou.test;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.mapper.IOrderMapper;
import com.lagou.mapper.IUserMapper;
import com.lagou.mapper.UserMapper;
import com.lagou.pojo.Order;
import com.lagou.pojo.Role;
import com.lagou.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {


    private IUserMapper iUserMapper = null;

    private IOrderMapper iOrderMapper = null;


    @Before
    public void before() throws IOException {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        iUserMapper = sqlSession.getMapper(IUserMapper.class);
        iOrderMapper = sqlSession.getMapper(IOrderMapper.class);
    }


    /**
     * 一对一:一个订单对应一个用户
     *
     * @throws IOException
     */
    @Test
    public void test1() {
        List<Order> orderNUser = iOrderMapper.findOrderAndUser();
        for (Order order : orderNUser) {
            System.out.println(order);
        }
    }


    /**
     * 一对多:一个用户对应多个订单
     *
     * @throws IOException
     */
    @Test
    public void test2() {
        List<User> userNOrder = iUserMapper.findUserAndOrder();
        for (User user : userNOrder) {
            System.out.println(user);
        }
    }


    /**
     * 多对多:用户对应角色
     */
    @Test
    public void test3() {
        List<User> userNRole = iUserMapper.findUserAndRole();
        for (User user : userNRole) {
            System.out.println(user);
        }
    }


    // Mybatis注解开发 ---开始

    /**
     * 测试新增用户
     */
    @Test
    public void testAddUser() {
        User user = new User();
        user.setId(7);
        user.setUsername("新增用户");
        iUserMapper.addUser(user);
    }


    /**
     * 测试修改用户
     */
    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(7);
        user.setUsername("修改用户");
        iUserMapper.updateUser(user);
    }

    /**
     * 测试查询用户
     */
    @Test
    public void testSelectUser() {
        List<User> userList = iUserMapper.selectUser();
        for (User user : userList) {
            System.out.println(user);
        }

    }

    /**
     * 测试根据id删除用户
     */
    @Test
    public void testDelete() {
        iUserMapper.deleteUser(7);
    }


    /**
     * 一对一:一个订单对应一个用户
     *
     * @throws IOException
     */
    @Test
    public void oneToOne() {
        List<Order> orderNUser = iOrderMapper.findOrderAndUser();
        for (Order order : orderNUser) {
            System.out.println(order);
        }
    }


    /**
     * 一对多:一个用户对应多个订单
     *
     * @throws IOException
     */
    @Test
    public void oneToMany() {
        List<User> userNOrder = iUserMapper.findUserAndOrder();
        for (User user : userNOrder) {
            System.out.println(user);
        }
    }


    /**
     * 一对多:一个用户对应多个订单
     *
     * @throws IOException
     */
    @Test
    public void ManyToMany() {
        List<User> userNRole = iUserMapper.findUserAndRole();
        for (User user : userNRole) {
            System.out.println(user);
        }
    }


    // Mybatis注解开发 ---结束


    @Test
    public void testPageHelperPlugin() {
        // 从每页显示两条，从第二页开始
        PageHelper.startPage(2, 2);
        List<User> userList = iUserMapper.selectUser();
        for (User user : userList) {
            System.out.println(user);
        }

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        System.out.println("总条数:" + pageInfo.getTotal());
        System.out.println("当前页总条数:" + pageInfo.getSize());
        System.out.println("总页数:" + pageInfo.getPages());
        System.out.println("当前页:" + pageInfo.getPageNum());
        System.out.println("每页显示的条数" + pageInfo.getPageSize());
        System.out.println("下一页页码" + pageInfo.getNextPage());
        System.out.println("上一页编号" + pageInfo.getLastPage());
    }

    /**
     * 测试通 用Mapper
     *
     * @throws IOException
     */
    @Test
    public void testMapper() throws IOException {
        InputStream in = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 单用户查询
        System.out.println("单用户查询");
        User user = new User();
        user.setId(1);
        User user1 = userMapper.selectOne(user);
        System.out.println(user1);
        // 查询所有用户
        System.out.println("查询所有用户:");
        List<User> users = userMapper.select(null);
        for (User user3 : users) {
            System.out.println(user3);
        }
        System.out.println("根据主键查询用户");
        User user4 = userMapper.selectByPrimaryKey(1);
        System.out.println(user4);

        // 新增用户
        User user2 = new User();
        user2.setId(7);
        user2.setUsername("caocao1");
        //userMapper.insert(user2);

        // 根据主键更新用户
        //userMapper.updateByPrimaryKey(user2);

        // 根据实体属性作为删除条件
        //userMapper.delete(user);

        // 根据主键进行删除
        //userMapper.deleteByPrimaryKey(7);
    }
}
