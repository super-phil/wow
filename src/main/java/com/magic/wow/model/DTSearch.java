package com.magic.wow.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zhaoxf on 2017/3/6.
 */
@Data
@NoArgsConstructor
public class DTSearch {
    private String value;
    private String regex;

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value.equals("") ? null : value;
    }
}
