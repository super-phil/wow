package com.magic.wow.mapper;

import com.magic.wow.model.DTRequest;
import com.magic.wow.model.Dkp;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhaoxf on 2017/3/8.
 */
@Mapper
public interface DkpMapper {
    String TABLE = "dkp";

    @Insert("INSERT INTO " + TABLE + " (#{dkp})")
    @Lang(SimpleInsertLangDriver.class)
    void add(Dkp dkp);

    //    @Select("SELECT * FROM " + TABLE + " ORDER BY createTime DESC LIMIT #{dt.start}, #{dt.length}")
    @SelectProvider(type = DkpProvider.class, method = "pageByQuery")
    List<Dkp> pageByQuery(DTRequest dtRequest);

    //    @Select("SELECT COUNT(1) FROM " + TABLE)
    @SelectProvider(type = DkpProvider.class, method = "countByQuery")
    Long countByQuery(DTRequest dtRequest);

    @Delete("DELETE FROM " + TABLE + " WHERE id=#{id}")
    int del(@Param("id") int id);

    @Select("SELECT * FROM " + TABLE + " WHERE id=#{id}")
    Dkp findById(@Param("id") int id);
}
