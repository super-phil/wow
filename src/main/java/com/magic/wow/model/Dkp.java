package com.magic.wow.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * DROP TABLE IF EXISTS `dkp`;
 * CREATE TABLE `dkp` (
 * `id` BIGINT  AUTO_INCREMENT COMMENT '主键',
 * `uid` BIGINT NOT NULL COMMENT '用户ID',
 * `username` CHAR (50) DEFAULT NULL COMMENT '用户名',
 * `type` CHAR (12) DEFAULT NULL COMMENT '消费类型',
 * `num` INT DEFAULT 0 COMMENT '分值',
 * `info` VARCHAR (256) DEFAULT NULL COMMENT '备注',
 * `createTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 * PRIMARY KEY (`id`),
 * KEY (`username`)
 * ) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'DKP表';
 * Created by zhaoxf on 2017/3/8.
 */
@Data
public class Dkp implements Serializable {
    private static final long serialVersionUID = 5817122938687104147L;
    private long id;
    private long uid;
    private String username;//会员名称
    private String role;//职业
    private String type;//消费类型
    private int num;//分值
    private String info;//备注
    private Date createTime;//时间
}
