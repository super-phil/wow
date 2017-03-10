package com.magic.wow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by zhaoxf on 2017/3/6.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Msg implements Serializable {
    private static final long serialVersionUID = -7761970428777599005L;
    private int code;
    private String msg;
    private Object obj;
}
