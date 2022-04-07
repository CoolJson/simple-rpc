package com.cooljson.codec;

/**
 * @author ChenJie
 * @date 2022/4/3
 * 序列化接口
 */

public interface Encoder {
    // 将对象转换为二进制数组
    byte[] encode(Object obj);
}
