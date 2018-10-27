package com.jenpin.gointo.netty.socket;

import com.jenpin.gointo.netty.Handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Server端：监听客户端请求，收到 TIME NOW 参数，然后返回当前时间
 * @author: Jenpin
 * @date: 2018/10/27 19:25
 * @email: yuan_268311@163.com
 * @description:
 **/
@Slf4j
public class TimeServer {

    private final static Integer PORT = 8027;
    public void run() throws Exception{
        /**
         * 创建两个 EventLoopGroup 实例,主要是将接受连接和处理连接请求任务划分开
         * bossGroup 用于接受客户端连接，bossGroup 在接受到客户端连接后，将连接交给workerGroup 进行处理
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        /**
         * 创建ServerBootstrap 服务端启动类实例
         */
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class) // 指定NioServerSocketChannel 服务端类，用于接受客户端连接，在接收到客户端连接后，会回调ChannelInitializer的initChannel方法。对这个连接进行初始化工作
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 设置匿名内部类ChannelInitializer实例，用于初始化客户端连接SocketChannel实例
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            /**
                             * LineBasedFrameDecoder、StringDecoder 用于解决TCP粘包，解包的工具类
                             * LineBasedFrameDecoder 在解析客户端请求时，遇到字符"\n"或"\r\n"时则认为是一个完整的请求报文，然后将这个请求报文的二进制字节流交给StringDecoder处理
                             * StringDecoder 将字节流转换成一个字符串，交给TimeServerHandler处理
                             * TimeServerHandler 为我们自己要编写的类，在这个类中，我们要根据用户请求返回当前时间
                             */
                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new TimeServerHandler());
                        }
                    });

            ChannelFuture future = bootstrap.bind(PORT).sync();
            log.info("TimeServer Started on "+PORT);
            future.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            log.info("TimeServer is stop!");
        }

    }

    public static void main(String[] args) throws Exception {
        new TimeServer().run();
    }
}
