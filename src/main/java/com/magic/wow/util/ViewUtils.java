package com.magic.wow.util;

/**
 * Created by Phil on 2016/4/20.
 */
public class ViewUtils {
    /**
     * Instantiates a new View utils.
     */
    public ViewUtils() {
    }

    /**
     * Is null boolean.
     *
     * @param obj the obj
     * @return the boolean
     */
    public boolean isNull(Object obj) {
        return null == obj;
    }

    /**
     * Is not null boolean.
     *
     * @param obj the obj
     * @return the boolean
     */
    public static boolean isNotNull(Object obj) {
        return null != obj;
    }
}
