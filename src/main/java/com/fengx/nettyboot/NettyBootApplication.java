package com.fengx.nettyboot;

import com.fengx.nettyboot.config.ThreadPoolConfig;
import com.fengx.nettyboot.http.server.NettyHttpServer;
import com.fengx.nettyboot.tcp.server.NettyTcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

@EnableAsync // 开启异步线程
@SpringBootApplication
public class NettyBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyBootApplication.class, args);

        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();
        Executor executor = threadPoolConfig.taskExecutor();
        // 启动HTTP服务
        executor.execute(new NettyHttpServer(8801));
        // 启动TCP服务
        executor.execute(new NettyTcpServer(8800));
    }

}
