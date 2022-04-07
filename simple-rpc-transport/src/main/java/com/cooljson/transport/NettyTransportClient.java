package com.cooljson.transport;

import com.cooljson.Peer;

import java.io.InputStream;

/**
 * @author ChenJie
 * @date 2022/4/4
 * 基于Netty的实现
 */

public class NettyTransportClient implements TransportClient{

    @Override
    public void connect(Peer peer) {

    }

    @Override
    public InputStream write(InputStream data) {
        return null;
    }

    @Override
    public void close() {

    }
}
