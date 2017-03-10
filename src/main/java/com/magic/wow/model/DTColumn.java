package com.magic.wow.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zhaoxf on 2017/3/6.
 */
@Data
@NoArgsConstructor
public class DTColumn {
    private String data;
    private String name;
    private boolean searchable;
    private boolean orderable;
    private DTSearch search;
}
