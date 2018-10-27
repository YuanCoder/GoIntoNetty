package com.jenpin.gointo.netty.Handler;/**
 * Created by Administrator on 2018/10/27.
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * 用于处理客户端请求
 * @author: Jenpin
 * @date: 2018/10/27 20:04
 * @email: yuan_268311@163.com
 * @description:
 **/
public class TimeServerHandler extends ChannelInboundHandlerAdapter{
    /**
     * 重写 channelRead 方法，当客户端发送了请求后，channelRead方法会被回调
     * @param context 当前发送请求的客户端上下文信息
     * @param message 客户端发送的信息
     */
    @Override
    public void channelRead(ChannelHandlerContext context, Object message){
        String request = (String) message; //直接将 message 强转为String类，因为前面加了 StringDecoder
        String response = null; //构建响应
        if("TIME NOW".equals(request)){
            response = new Date(System.currentTimeMillis()).toString();
        }else{
            response = "BAD REQUEST";
        }
        response = response + System.getProperty("line.separator"); //在响应内容上家换行符(System.getProperty("line.separator")),客户端解码时，判定遇到换行符，就认为是一个完整的响应
        ByteBuf resp = Unpooled.copiedBuffer(response.getBytes()); // 创建缓冲区对象
        context.writeAndFlush(resp); //响应刷到客户端
    }
}
