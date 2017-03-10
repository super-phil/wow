package com.magic.wow.util;

import org.apache.shiro.codec.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Created by Phil on 2016/4/25.
 */
public class GenerateAESKey {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println(Base64.encodeToString(initKey()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Init key byte [ ].
     *
     * @return the byte [ ]
     * @throws Exception the exception
     */
    public static byte[] initKey() throws Exception {
        //实例化
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //设置密钥长度
        kgen.init(128);
        //生成密钥
        SecretKey skey = kgen.generateKey();
        //返回密钥的二进制编码
        return skey.getEncoded();
    }
}
