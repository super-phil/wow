package com.magic.wow.mapper;

import com.magic.wow.model.DTRequest;
import com.magic.wow.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoxf on 2017/3/6.
 */
@Mapper
public interface UserMapper {
    String TABLE = "user";

    @Select("SELECT * FROM " + TABLE + " WHERE username = #{username} AND pwd = #{pwd}")
    User findByName(@Param("username") String username, @Param("pwd") String pwd);

    @Insert("INSERT INTO " + TABLE + " (#{user})")
    @Lang(SimpleInsertLangDriver.class)
    int insert(User user);

    @SelectProvider(type = UserProvider.class, method = "pageByQuery")
    List<User> pageByQuery(DTRequest dtRequest);

    @SelectProvider(type = UserProvider.class, method = "countByQuery")
    long countByQuery(DTRequest dtRequest);

    @Delete("DELETE FROM " + TABLE + " WHERE id=#{id}")
    int del(@Param("id") int id);

    @Update("UPDATE " + TABLE + " SET dynamic=dynamic+#{num} WHERE id=#{uid}")
    void updateDynamic(@Param("num") int num, @Param("uid") long uid);

    @Update("UPDATE " + TABLE + " SET consume=consume-#{num} WHERE id=#{uid}")
    void updateConsume(@Param("num") int num, @Param("uid") long uid);

}
