package com.magic.wow.util;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by Phil on 2016/4/25.
 */
public class SerializeUtils {
    private static final Logger logger = Logger.getLogger(SerializeUtils.class);

    /**
     * 反序列化
     *
     * @param bytes the bytes
     * @return object
     */
    public static Object deserialize(byte[] bytes) {

        Object result = null;

        if (isEmpty(bytes)) {
            return null;
        }

        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
                try {
                    result = objectInputStream.readObject();
                } catch (ClassNotFoundException ex) {
                    throw new Exception("Failed to deserialize object type", ex);
                }
            } catch (Throwable ex) {
                throw new Exception("Failed to deserialize", ex);
            }
        } catch (Exception e) {
            logger.error("Failed to deserialize", e);
        }
        return result;
    }

    /**
     * Is empty boolean.
     *
     * @param data the data
     * @return the boolean
     */
    public static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    /**
     * 序列化
     *
     * @param object the object
     * @return byte [ ]
     */
    public static byte[] serialize(Object object) {

        byte[] result = null;

        if (object == null) {
            return new byte[0];
        }
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
            try {
                if (!(object instanceof Serializable)) {
                    throw new IllegalArgumentException(SerializeUtils.class.getSimpleName() + " requires a Serializable payload " +
                            "but received an object of type [" + object.getClass().getName() + "]");
                }
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
                objectOutputStream.writeObject(object);
                objectOutputStream.flush();
                result = byteStream.toByteArray();
            } catch (Throwable ex) {
                throw new Exception("Failed to serialize", ex);
            }
        } catch (Exception ex) {
            logger.error("Failed to serialize", ex);
        }
        return result;
    }
}
