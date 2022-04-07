package com.cooljson.codec;

import com.alibaba.fastjson.JSON;

/**
 * @author ChenJie
 * @date 2022/4/3
 * 基于json的反序列化实现类
 */

public class JSONDecoder implements Decoder{
    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
