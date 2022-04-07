package com.cooljson.client;

import com.cooljson.Peer;
import com.cooljson.transport.TransportClient;

import java.util.List;

/**
 * @author ChenJie
 * @date 2022/4/4
 * server选择路由类(选择那个server去连接)
 */

public interface TransportSelector {

    /**
     * 初始化选择器
     * @param peers    可以连接的所有网络端点信息
     * @param count    client与server的连接数
     * @param clazz    client的实现类class
     */
    void init(List<Peer> peers,
              int count,
              Class<? extends TransportClient> clazz);

    /**
     * 选择一个transport与server做交互
     * @return  网络client
     */
    TransportClient select();

    // 释放使用完毕的client
    void release(TransportClient client);

    // 关闭整个路由选择器
    void close();
}
