package com.magic.wow.model;

import lombok.Data;
import org.apache.shiro.crypto.hash.Sha512Hash;

import java.io.Serializable;

/**
 * Created by zhaoxf on 2017/3/6.
 * CREATE TABLE `user` (
 * `id` BIGINT (20) AUTO_INCREMENT COMMENT '主键',
 * `username` VARCHAR (50) DEFAULT NULL COMMENT '用户名',
 * `pwd` VARCHAR (50) DEFAULT NULL COMMENT '密码',
 * `mark` INT (11) DEFAULT 0 COMMENT '状态[0:正常,1:异常]',
 * `type` VARCHAR (4) DEFAULT NULL COMMENT '类型',
 * `desc` VARCHAR (128) DEFAULT NULL COMMENT '备注',
 * `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 * PRIMARY KEY (`id`),
 * KEY (`username` , `pwd`),
 * KEY (`type`)
 * ) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表';
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 6594065753502938087L;
    //初始分	加分	调节分	消费	当前	操作
    private long id;
    private String username;//会员名称
    private String pwd;
    private int mark;//FB MC NAXX
    private String type;//职业
    private int dynamic;//动态分
    private int consume;//消费分
    private int surplus;//剩余分
    private String role;//角色
}
