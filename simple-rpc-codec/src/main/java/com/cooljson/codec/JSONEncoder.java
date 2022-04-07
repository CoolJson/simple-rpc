package com.cooljson.codec;

import com.alibaba.fastjson.JSON;

/**
 * @author ChenJie
 * @date 2022/4/3
 * 基于json的序列化实现类
 */

public class JSONEncoder implements Encoder{
    @Override
    public byte[] encode(Object obj) {
        return JSON.toJSONBytes(obj);
    }
}
