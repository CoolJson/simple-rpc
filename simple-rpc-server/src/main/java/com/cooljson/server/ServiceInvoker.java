package com.cooljson.server;

import com.cooljson.Request;
import com.cooljson.common.utils.ReflectionUtils;

/**
 * @author ChenJie
 * @date 2022/4/4
 * 负责调用具体的服务
 */

public class ServiceInvoker {

    // 调用服务
    public Object invoke(ServiceInstance service, Request request){
        return ReflectionUtils.invoke(
                service.getTarget(),
                service.getMethod(),
                request.getParameters()
        );
    }
}
