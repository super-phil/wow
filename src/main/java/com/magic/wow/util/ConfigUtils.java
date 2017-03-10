/**
 *
 */
package com.magic.wow.util;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * 配置工具
 */
public class ConfigUtils {
//    private static final Logger logger = Logger.getLogger(ConfigUtils.class);
//    private static Properties prop = null;
//
//    private ConfigUtils() {
//    }
//
//    static {
//        try {
//            InputStream is = ConfigUtils.class.getClassLoader().getResourceAsStream("config.properties");
//            prop = new Properties();
//            prop.load(is);
//            is.close();
//        } catch (IOException e) {
//            logger.error("load config error", e);
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * Get string.
//     *
//     * @param key the key
//     * @return the string
//     */
//    public static String get(String key) {
//        return prop.getProperty(key);
//    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        ArrayList<Object> objects = Lists.newArrayList();
        objects.add("a");
        objects.add("b");
        objects.add("c");
        System.out.println(Joiner.on(",").skipNulls().join(objects));
//        System.out.println(get("mail.username"));
    }
}
