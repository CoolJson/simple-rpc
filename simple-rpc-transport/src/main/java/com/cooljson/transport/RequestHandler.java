package com.cooljson.transport;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author ChenJie
 * @date 2022/4/3
 * 处理网络请求的Handler接口
 */

public interface RequestHandler {
    /**
     * 调用请求中指定的服务
     * @param receive   序列化后的Request请求
     * @param toResp    需要返回的数据(将响应体序列化后写入输出流中)
     */
    void onRequest(InputStream receive, OutputStream toResp);
}
