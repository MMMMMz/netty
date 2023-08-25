package com.meituan.netty.client;

import com.meituan.netty.handler.ClientChannelInitializer;
import com.meituan.netty.handler.NettyClientHandler;
import com.meituan.netty.handler.NettyServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mazhe
 * @date 2023/8/25 20:12
 */
@Slf4j
public class NettyClient {
    public static String helloNetty(String msg) {
        NettyClientHandler nettyClientHandler = new NettyClientHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                //该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输
                .option(ChannelOption.TCP_NODELAY, true)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast("decoder", new StringDecoder());
                        socketChannel.pipeline().addLast("encoder", new StringEncoder());
                        socketChannel.pipeline().addLast(nettyClientHandler);
                    }
                });
        try {
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8082).sync();
            //发送消息
            future.channel().writeAndFlush(msg);
            log.info("客户端发送成功....");
            // 等待连接被关闭
            future.channel().closeFuture().sync();
            log.info("客户端等待连接被关闭....");
            return nettyClientHandler.getResponseResult();
        } catch (Exception e) {
            log.error("客户端Netty失败", e);
        } finally {
            //以一种优雅的方式进行线程退出
            group.shutdownGracefully();
        }
        return nettyClientHandler.getResponseResult();
    }
}
