package com.magic.wow.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zhaoxf on 2017/3/6.
 */
@Data
@NoArgsConstructor
public class DTOrder {
    private int column;//排序的列索引 从0开始
    private String dir;//排序规则 asc?desc
}
