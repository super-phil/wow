package com.magic.wow.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhaoxf on 2017/3/6.
 */
@Data
@NoArgsConstructor
public class DTResponse<T> implements Serializable{

    private static final long serialVersionUID = -7031375338413546730L;
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private List<T> data;
}
