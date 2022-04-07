package com.cooljson.server;

import com.cooljson.codec.Decoder;
import com.cooljson.codec.Encoder;
import com.cooljson.codec.JSONDecoder;
import com.cooljson.codec.JSONEncoder;
import com.cooljson.transport.HTTPTransportServer;
import com.cooljson.transport.TransportServer;
import lombok.Data;

/**
 * @author ChenJie
 * @date 2022/4/4
 * server配置类
 */

@Data
public class RpcServerConfig {

    // 默认使用HTTP的server实现
    private Class<? extends TransportServer> transportClass = HTTPTransportServer.class;

    // 配置序列化方式默认使用JSON实现
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;

    // 配置反序列化方式默认使用JSON实现
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;

    // 默认监听端口
    private int port = 3000;
}
