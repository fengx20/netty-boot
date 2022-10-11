package com.fengx.nettyboot.tcp.client;


import com.fengx.nettyboot.tcp.entity.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;

/**
 * @author Fengx
 * @description：客户端
 **/
public class TcpClient {

    // 要请求的服务器的ip地址
    private String ip;
    // 服务器的端口
    private int port;

    public TcpClient(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.connect(ip,port);
    }

    @SneakyThrows
    public void connect(String host, int port){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new TcpClientInitializer()); // 初始化

            // 开启客户端
            ChannelFuture f = b.connect(host, port).sync();
            System.out.println("向 ip: " + host + " , port:" + port + " 的服务器 发送请求 ");

            if(f.isSuccess()){
                System.out.println("客户端启动成功");
            } else {
                System.out.println("客户端启动失败");
                f.cause().printStackTrace();
            }

            // 要发送的数据
            RpcRequest rpcRequest = new RpcRequest();
            rpcRequest.setId("100");
            rpcRequest.setData("我是 tcp 客户端111。。。。。。。。。。。。");

            // 输出
            f.channel().writeAndFlush(rpcRequest);

            // 等待直到连接中断
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }


    public static void main(String[] args){
        new TcpClient("127.0.0.1", 8800);
    }

}
