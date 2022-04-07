package com.cooljson.client;

import com.cooljson.Request;
import com.cooljson.Response;
import com.cooljson.ServiceDescriptor;
import com.cooljson.codec.Decoder;
import com.cooljson.codec.Encoder;
import com.cooljson.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author ChenJie
 * @date 2022/4/4
 * 调用远程服务的代理类
 */

@Slf4j
public class RemoteInvoker implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(RemoteInvoker.class);

    private Class clazz;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    public RemoteInvoker(Class clazz,
                         Encoder encoder,
                         Decoder decoder, TransportSelector selector) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
        this.selector = selector;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        /**
         * 1、构造请求
         * 2、选择连接
         * 3、序列化请求
         * 4、发送序列化后的请求
         * 5、将响应结果反序列化
         */
        Request request = new Request();
        request.setService(ServiceDescriptor.newInstance(clazz, method));
        request.setParameters(args);

        Response resp = invokeRemote(request);
        if (resp == null || resp.getCode() != 200) {
            throw new IllegalStateException("fail to invoke remote: " + resp);
        }

        return resp.getData();
    }

    // 通过网络传输层调用远程服务
    private Response invokeRemote(Request request) {
        TransportClient client = null;
        Response response = null;
        try {
            // 选择连接
            client = selector.select();

            byte[] outBytes = encoder.encode(request);
            // 发送请求数据并获取响应结果
            InputStream receive = client.write(new ByteArrayInputStream(outBytes));

            // 反序列化为响应体
            byte[] inBytes = IOUtils.readFully(receive, receive.available());
            response = decoder.decode(inBytes, Response.class);
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
            response.setCode(999);
            response.setMessage("RpcClient git error: " + e.getClass()
                    + " : " + e.getMessage());
        } finally {
            // 释放连接
            if (client != null) {
                selector.release(client);
            }
        }
        return response;
    }
}
