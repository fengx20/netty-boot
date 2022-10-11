package com.fengx.nettyboot.http.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

/**
 * @author Fengx
 * @description：客户端
 **/
public class HttpClient {

    public void connect(String host, int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    // HTTP编码
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    // HTTP解码
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    // 业务处理器（自定义）
                    ch.pipeline().addLast(new HttpClientHandler());
                }
            });

            // 建立长连接
            ChannelFuture f = b.connect(host, port).sync();
            System.out.println("客户端信息：netty http client connected on host(" + host + ") port(" + port + ")");
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new HttpClient().connect("127.0.0.1", 8801);
    }
}
