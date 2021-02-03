package com.lagou.mapper;

import com.lagou.pojo.Order;
import com.lagou.pojo.User;
import org.apache.ibatis.annotations.*;
import org.mybatis.caches.redis.RedisCache;

import java.util.List;


@CacheNamespace(implementation = RedisCache.class) // 开启二级缓存
public interface IOrderMapper {

    /**
     * 查询订单的同时，还要查询订单所属用户    一对一
     */
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "total",column = "total"),
            @Result(property = "user",column = "uid",javaType = User.class,one=@One(select = "com.lagou.mapper.IUserMapper.findUserById"))
    })
    @Select("select * from orders")
    List<Order> findOrderAndUser();


    /**
     * 根据用户id查询订单
     */
    @Select("select * from orders where uid = #{uid}")
    List<Order> findOrderById(Integer uid);
}
