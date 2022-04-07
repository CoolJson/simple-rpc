package com.cooljson.transport;

/**
 * @author ChenJie
 * @date 2022/4/3
 */

public interface TransportServer {

    // 初始化的时候开始接受请求
    void init(int port, RequestHandler handler);

    // 启动监听
    void start();

    // 关闭监听
    void stop();
}
