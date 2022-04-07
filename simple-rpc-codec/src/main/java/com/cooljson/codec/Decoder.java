package com.cooljson.codec;

/**
 * @author ChenJie
 * @date 2022/4/3
 * 反序列化接口
 */

public interface Decoder {
    // 将二进制数组转换为对象
    <T> T decode(byte[] bytes, Class<T> clazz);
}
