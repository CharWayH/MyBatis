package com.lagou.mapper;

import com.lagou.pojo.Role;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;
import org.mybatis.caches.redis.RedisCache;

import java.util.List;

@CacheNamespace(implementation = RedisCache.class) // 开启二级缓存
public interface IRoleMapper {
    /**
     * 根据uid查询Role
     * @param uid
     * @return
     */
    @Select("select * from role r,user_role ur where r.id = ur.roleid and ur.userid = #{uid}")
    List<Role> findRoleByUid(Integer uid);
}