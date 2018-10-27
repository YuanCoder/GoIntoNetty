package com.jenpin.gointo.netty.Handler;/**
 * Created by Administrator on 2018/10/27.
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 用于给服务端发送请求以及接受服务端响应
 * @author: Jenpin
 * @date: 2018/10/27 21:08
 * @email: yuan_268311@163.com
 * @description:
 **/
@Slf4j
public class TimeClientHandler extends ChannelInboundHandlerAdapter{

    private byte[] request = ("TIME NOW"+System.getProperty("line.separator")).getBytes();

    /**
     * 当客户端与服务端连接建立成功后，channelActive会被回调，
     * 在这个方法中给服务端发送请求
     * @param context
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception{
        ByteBuf message = Unpooled.buffer(request.length);
        message.writeBytes(request);
        context.writeAndFlush(message);
    }

    /**
     * 当客户端接收到服务端响应后，channelRead方法会被回调
     * @param context
     * @param message
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext context, Object message) throws Exception {
        String body = (String) message;
        log.info("Now is:"+body);
    }
}
