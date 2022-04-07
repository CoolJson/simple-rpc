package com.cooljson.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenJie
 * @date 2022/4/3
 * 反射工具类
 */

public class ReflectionUtils {

    /**
     * 根据class使用反射创建对象
     * @param clazz  待创建对象的类
     * @param <T>    对象类型
     * @return       创建好的对象
     */
    public static <T> T newInstance(Class<T> clazz){
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取class的所有公共方法
     * @param clazz  对象的类
     * @return       当前类的所有公共方法
     */
    public static Method[] getPublicMethods(Class clazz){
        // 返回当前类的所有方法
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> list = new ArrayList<>();
        for (Method method : methods) {
            // 判断是否是public类型
            if (Modifier.isPublic(method.getModifiers())) {
                list.add(method);
            }
        }
        return list.toArray(new Method[0]);
    }

    /**
     * 调用指定对象的指定方法
     * @param obj      被调用方法的对象
     * @param method   被调用的方法
     * @param args     参数列表
     * @return         执行结果
     */
    public static Object invoke(Object obj, Method method, Object... args){
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
