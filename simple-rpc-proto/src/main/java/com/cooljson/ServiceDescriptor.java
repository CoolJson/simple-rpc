package com.cooljson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author ChenJie
 * @date 2022/4/3
 * 表示某个服务
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDescriptor {
    // 服务的类名
    private String clazz;

    // 服务的方法名
    private String method;

    // 服务的返回值类型
    private String returnType;

    // 服务的参数类型
    private String[] parameterTypes;

    public static ServiceDescriptor newInstance(Class clazz, Method method){
        ServiceDescriptor sd = new ServiceDescriptor();
        sd.setClazz(clazz.getName());
        sd.setMethod(method.getName());
        sd.setReturnType(method.getReturnType().getName());

        Class<?>[] parameterClasses = method.getParameterTypes();
        String[] parameterTypes = new String[parameterClasses.length];
        for (int i = 0; i < parameterClasses.length; i++) {
            parameterTypes[i] = parameterClasses[i].getName();
        }
        sd.setParameterTypes(parameterTypes);
        return sd;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        // 判断类型
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ServiceDescriptor sd = (ServiceDescriptor) obj;
        return this.toString().equals(sd.toString());
    }

    @Override
    public String toString() {
        return "clazz=" + clazz
                + ",method=" + method
                + ",returnType=" + returnType
                + ",parameterTypes=" + Arrays.toString(parameterTypes);
    }
}
