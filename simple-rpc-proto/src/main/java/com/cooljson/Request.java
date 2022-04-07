package com.cooljson;

import lombok.Data;

/**
 * @author ChenJie
 * @date 2022/4/3
 * simple-rpc的请求体
 */

@Data
public class Request {
    // 表示请求的某个服务
    private ServiceDescriptor service;

    // 传入的参数数组
    private Object[] parameters;
}
