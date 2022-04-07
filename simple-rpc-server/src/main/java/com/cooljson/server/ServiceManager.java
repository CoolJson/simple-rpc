package com.cooljson.server;

import com.cooljson.Request;
import com.cooljson.ServiceDescriptor;
import com.cooljson.common.utils.ReflectionUtils;
import com.cooljson.transport.HTTPTransportServer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ChenJie
 * @date 2022/4/4
 * rpc的服务管理类
 */

@Slf4j
public class ServiceManager {

    private static final Logger logger = LoggerFactory.getLogger(ServiceManager.class);

    // 服务的容器,管理所有注册的服务
    private Map<ServiceDescriptor, ServiceInstance> services;

    public ServiceManager() {
        this.services = new ConcurrentHashMap<>();
    }

    /**
     * 注册服务(将接口中的所有方法分别封装为服务并进行注册)
     * @param interfaceClass    接口的class
     * @param bean              接口的实例对象
     * @param <T>               对象类型
     */
    public <T> void register(Class<T> interfaceClass, T bean){
        // 获取接口中的所有公共方法
        Method[] methods = ReflectionUtils.getPublicMethods(interfaceClass);
        for (Method method : methods) {
            ServiceInstance serviceInstance = new ServiceInstance(bean, method);
            ServiceDescriptor serviceDescriptor = ServiceDescriptor.newInstance(interfaceClass, method);

            services.put(serviceDescriptor, serviceInstance);
            logger.info("register service: {} {}", serviceDescriptor.getClazz(), serviceDescriptor.getMethod());
        }
    }

    // 查找服务
    public ServiceInstance findService(Request request){
        ServiceDescriptor sd = request.getService();
        return services.get(sd);
    }
}
