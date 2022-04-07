package com.cooljson.transport;

import com.cooljson.Peer;

import java.io.InputStream;

/**
 * @author ChenJie
 * @date 2022/4/3
 */

public interface TransportClient {

    // 创建连接
    void connect(Peer peer);

    // 发送数据,并等待响应
    InputStream write(InputStream data);

    // 关闭连接
    void close();
}
