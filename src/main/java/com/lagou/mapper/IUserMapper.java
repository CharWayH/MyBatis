package com.lagou.mapper;


import com.lagou.pojo.User;
import org.apache.ibatis.annotations.*;
import org.mybatis.caches.redis.RedisCache;

import java.util.List;

@CacheNamespace(implementation = RedisCache.class) // 开启二级缓存
public interface IUserMapper {


    /**
     *  查询所有用户信息，并且查询出所有用户关联的订单信息
      * @return 用户列表
     */
    @Select("select * from user")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "orderList",column = "id",javaType = List.class,many=@Many(select = "com.lagou.mapper.IOrderMapper.findOrderById"))
    })
    List<User> findUserAndOrder();


    /**
     * 查看所有用户信息并且包含其角色信息
     */
    @Select("select * from user")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "roleList", column = "id", javaType = List.class, many = @Many(select = "com.lagou.mapper.IRoleMapper.findRoleByUid"))
    }
    )
    List<User>  findUserAndRole();


    /**
     * 新增用户
     */
    @Insert("insert into user values(#{id},#{username})")
    void addUser(User user);


    /**
     * 修改用户
     */
    @Update("update user set username = #{username} where id = #{id}")
    void updateUser(User user);

    /**
     * 查询所有用户
     */
    @Select("select * from user")
    List<User> selectUser();


    /**
     * 根据id删除用户
     */
    @Delete("delete from user where id = #{id}")
    void deleteUser(Integer id);


    /**
     * 根据id查询用户
     */
    @Select("select * from user where id = #{id}")
    User findUserById(Integer id);
}