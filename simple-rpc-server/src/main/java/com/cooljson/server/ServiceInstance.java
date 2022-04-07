package com.cooljson.server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author ChenJie
 * @date 2022/4/4
 * 某个服务的具体实例
 */

@Data
@AllArgsConstructor
public class ServiceInstance {

    // 提供服务的目标对象
    private Object target;

    // 服务所对应的方法
    private Method method;
}
