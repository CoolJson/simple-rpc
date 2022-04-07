package com.cooljson.client;

import com.cooljson.Peer;
import com.cooljson.codec.Decoder;
import com.cooljson.codec.Encoder;
import com.cooljson.codec.JSONDecoder;
import com.cooljson.codec.JSONEncoder;
import com.cooljson.transport.HTTPTransportClient;
import com.cooljson.transport.TransportClient;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @author ChenJie
 * @date 2022/4/4
 * client配置类
 */

@Data
public class RpcClientConfig {

    // 默认使用HTTP的网络模块实现
    private Class<? extends TransportClient> transportClass = HTTPTransportClient.class;

    // 配置序列化方式默认使用JSON实现
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;

    // 配置反序列化方式默认使用JSON实现
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;

    // 配置路由选择,默认使用随机选择器
    private Class<? extends TransportSelector> transportSelectorClass = RandomTransportSelector.class;

    // 每一个端点建立的连接数, 默认建立1个连接
    private int connectCount = 1;

    // 可连接的端点信息
    private List<Peer> servers = Arrays.asList(
            new Peer("127.0.0.1", 3000)
    );
}
