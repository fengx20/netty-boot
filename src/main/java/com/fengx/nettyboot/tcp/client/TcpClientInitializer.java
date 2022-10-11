package com.fengx.nettyboot.tcp.client;

import com.fengx.nettyboot.tcp.coder.RpcDecoder;
import com.fengx.nettyboot.tcp.coder.RpcEncoder;
import com.fengx.nettyboot.tcp.entity.RpcRequest;
import com.fengx.nettyboot.tcp.entity.RpcResponse;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Fengx
 * @description：初始化
 **/
public class TcpClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel){
        System.out.println("初始化通道: Tcp client initChannel..");
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new RpcEncoder(RpcRequest.class));//TCP编码
        pipeline.addLast(new RpcDecoder(RpcResponse.class));//TCP解码
        pipeline.addLast(new TcpClientHandler());//业务处理器
    }
}
