package com.jenpin.gointo.netty.socket;/**
 * Created by Administrator on 2018/10/27.
 */

import com.jenpin.gointo.netty.Handler.TimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Host;

/**
 * 客户端
 * @author: Jenpin
 * @date: 2018/10/27 20:49
 * @email: yuan_268311@163.com
 * @description:
 **/
@Slf4j
public class TimeClient {
    private final static String HOST = "localhost";
    private final static Integer PORT = 8027;

    public static void main(String[] args) throws Exception{
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            final Bootstrap bootstrap = new Bootstrap(); //Bootstrap 实例表示客户端启动类 与 ServerBootstrap 相对应
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    socketChannel.pipeline().addLast(new StringDecoder());
                    socketChannel.pipeline().addLast(new TimeClientHandler());
                }
            });
            log.info("Start the client!");
            log.info("connect with:"+HOST+" "+PORT);
            ChannelFuture future = bootstrap.connect(HOST , PORT);
            log.info("Wait until the connection is closed!");
            future.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
        }

    }
}
