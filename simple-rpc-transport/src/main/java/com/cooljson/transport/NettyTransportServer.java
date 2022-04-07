package com.cooljson.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

/**
 * @author ChenJie
 * @date 2022/4/4
 * 基于Netty的实现
 */

@Slf4j
public class NettyTransportServer implements TransportServer{

    private static final Logger logger = LoggerFactory.getLogger(NettyTransportServer.class);

    private RequestHandler handler;
    private int port;

    @Override
    public void init(int port, RequestHandler handler) {
        this.handler = handler;
        this.port = port;
    }

    @Override
    public void start() {
        // 创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 设置启动对象
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    // 设置通道
                    .channel(NioServerSocketChannel.class)
                    // 设置通道的初始化对象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    // 添加编解码器
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    // 添加自定义处理器
                                    .addLast(new NettyServerHandler());
                        }
                    });

            // 绑定端口号, 并设置为同步启动服务
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            logger.info("Rpc Server started on port: {}", port);
            // 监听通道的关闭状态
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }finally {
            // 释放资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {

    }

    class NettyServerHandler extends SimpleChannelInboundHandler<String> {

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
            // 接收客户端请求
            // Request request = JSON.parseObject(s, Request.class);

            // 调用服务
            if (handler != null) {
                handler.onRequest(new ByteArrayInputStream(s.getBytes()), null);
            }

        }
    }
}
