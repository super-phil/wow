package com.magic.wow.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoxf on 2017/3/9.
 */
@Mapper
public interface ChartMapper {
    @Select("SELECT\n" +
            "\ta.username,\n" +
            "\t\ta.type,\n" +
            "\t\ta.dynamic + a.consume as v\n" +
            "FROM\n" +
            "\tuser a\n" +
            "WHERE\n" +
            "\t#{n} >= (\n" +
            "\t\tSELECT\n" +
            "\t\t\tCOUNT(1)\n" +
            "\t\tFROM\n" +
            "\t\t\tuser b\n" +
            "\t\tWHERE\n" +
            "\t\t\ta.type = b.type\n" +
            "\t\tAND a.dynamic + a.consume <= b.dynamic + b.consume\n" +
            "\t)\n" +
            "ORDER BY\n" +
            "\ta.type,\n" +
            "\ta.dynamic + a.consume DESC")
    List<Map<String, Object>> groupByType(@Param("n") int n);
}
