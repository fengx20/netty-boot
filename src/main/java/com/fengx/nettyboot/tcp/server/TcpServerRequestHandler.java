package com.fengx.nettyboot.tcp.server;

import com.fengx.nettyboot.tcp.entity.RpcRequest;
import com.fengx.nettyboot.tcp.entity.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;
import java.util.UUID;

/**
 * @author Fengx
 * @description：处理类
 **/
public class TcpServerRequestHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //返回此通道绑定到的本地地址(IP 地址 和 端口号)
        SocketAddress socketAddress = ctx.channel().localAddress();
        String str = socketAddress.toString();
        System.out.println("ip"+str);
        //截取端口号
        String str1=str.substring(0, str.indexOf(":"));
        String str2=str.substring(str1.length()+1, str.length());
        System.out.println("port: "+str2);

        /* 接收到 客户端发送来的数据 */
        RpcRequest request = (RpcRequest) msg;
        System.out.println("接收到客户端信息:" + request.toString());

        /* 返回 响应给 客户端 */
        RpcResponse response = new RpcResponse();
        response.setId(UUID.randomUUID().toString());
        response.setData("server响应结果");
        response.setStatus(1);
        //输出 数据 到 客户端
        ctx.writeAndFlush(response);
    }


    // 通知处理器最后的channelRead()是当前批处理中的最后一条消息时调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端接收数据完毕..");
        ctx.flush();
    }

    //读操作时捕获到异常时调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    //客户端去和服务端连接成功时触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("hello client");
    }

}
