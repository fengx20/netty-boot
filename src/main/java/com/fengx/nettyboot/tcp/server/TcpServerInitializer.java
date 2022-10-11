package com.fengx.nettyboot.tcp.server;

import com.fengx.nettyboot.tcp.coder.RpcDecoder;
import com.fengx.nettyboot.tcp.coder.RpcEncoder;
import com.fengx.nettyboot.tcp.entity.RpcRequest;
import com.fengx.nettyboot.tcp.entity.RpcResponse;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Fengx
 * @description：初始化类
 **/
public class TcpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println("初始化通道: Tcp server initChannel..");
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new RpcDecoder(RpcRequest.class));
        pipeline.addLast(new RpcEncoder(RpcResponse.class));
        pipeline.addLast(new TcpServerRequestHandler());
    }
}
