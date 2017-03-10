package com.magic.wow.mapper;

import com.magic.wow.model.DTRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by zhaoxf on 2017/3/7.
 */
public class UserProvider {
    public String pageByQuery(DTRequest dtRequest) {
        SQL sql = new SQL().SELECT("*").FROM(UserMapper.TABLE);
        valid(dtRequest, sql);
        //http://blog.csdn.net/boonya/article/details/15335523
        //https://segmentfault.com/q/1010000006875476/a-1020000006876046/revision
        return sql.toString() + " ORDER BY type, dynamic DESC LIMIT " + dtRequest.getStart() + "," + dtRequest.getLength();
    }

    public String countByQuery(DTRequest dtRequest) {
        SQL sql = new SQL().SELECT("COUNT(1)").FROM(UserMapper.TABLE);
        valid(dtRequest, sql);
        return sql.toString();
    }

    /**
     * 前台分页使用
     *
     * @param dtRequest
     * @param sql
     */
    private void valid(DTRequest dtRequest, SQL sql) {
        String search = dtRequest.getQ();
        if (!StringUtils.isBlank(search)) {
            sql.WHERE("username LIKE #{rightLike}");
        }
    }
}
