package com.fengx.nettyboot.tcp.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * @author Fengx
 * @description：服务端
 **/
public class NettyTcpServer implements Runnable {
    int port;

    public NettyTcpServer(int port) {
        this.port = port;
    }

    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup work = new NioEventLoopGroup();

    @Override
    public void run() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TcpServerInitializer());
//        .childHandler(new TcpServerInitializer())


            ChannelFuture f = bootstrap.bind(new InetSocketAddress(port)).sync();
//        System.out.println(" server start up on port : " + port);

            if (f.isSuccess()) {
                System.out.println("Tcp服务端启动成功");
            } else {
                System.out.println("Tcp服务端启动失败");
                f.cause().printStackTrace();
            }

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
