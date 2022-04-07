package com.cooljson.server;

import com.cooljson.Request;
import com.cooljson.Response;
import com.cooljson.ServiceDescriptor;
import com.cooljson.codec.Decoder;
import com.cooljson.codec.Encoder;
import com.cooljson.common.utils.ReflectionUtils;
import com.cooljson.transport.HTTPTransportServer;
import com.cooljson.transport.RequestHandler;
import com.cooljson.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

/**
 * @author ChenJie
 * @date 2022/4/4
 */

@Slf4j
public class RpcServer {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    private RpcServerConfig config;
    private TransportServer net;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    public RpcServer(RpcServerConfig config) {
        this.config = config;

        // 从配置类中获取,使用反射创建具体的实例对象
        this.net = ReflectionUtils.newInstance(config.getTransportClass());
        // 网络模块执行初始化
        this.net.init(config.getPort(), this.handler);
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());

        // 创建服务容器以及服务具体的调用对象
        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();
    }

    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream receive, OutputStream toResp) {

            Response resp = new Response();
            try {
                // 从receive中读取到数据（Request请求被序列化后的二进制数据）
                byte[] bytes = IOUtils.readFully(receive, receive.available());
                // 将读取到的数据反序列化还原为request请求
                Request request = decoder.decode(bytes, Request.class);

                logger.info("get request:{}", request);

                // 从容器中获取指定的服务后,使用invoker执行者调用服务,并将执行结果写入到响应体中
                ServiceInstance service = serviceManager.findService(request);
                Object result = serviceInvoker.invoke(service, request);
                resp.setData(result);
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
                resp.setCode(999);
                resp.setMessage("RpcServer got error: "
                        + e.getClass().getName() + " : "
                        + e.getMessage());
            }finally {
                // 使用toResp将响应体写回
                byte[] outBytes = encoder.encode(resp);
                try {
                    toResp.write(outBytes);
                    logger.info("response to client");
                } catch (IOException e) {
                    logger.warn(e.getMessage(), e);
                }
            }
        }
    };

    // 注册服务->调用容器的注册方法
    public <T> void register(Class<T> interfaceClass, T bean){
        serviceManager.register(interfaceClass, bean);
    }

    // 启动
    public void start(){
        // 开启网络监听
        this.net.start();
    }

    public void stop(){
        this.net.stop();
    }
}
