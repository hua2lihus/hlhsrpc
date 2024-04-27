package com.hlhs.hlhsrpc.serializer;

import java.io.IOException;

/**
 * 序列化器接口
 */
public interface Serializer {

    /**
     * 序列化
     * @param obj 对象
     * @return 字节数组
     */
   <T> byte[] serialize(T obj) throws IOException;

    /**
     * 反序列化
     * @param bytes 字节数组
     * @param clazz 类
     * @param <T> 泛型标记
     * @return 对象
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException;
}
