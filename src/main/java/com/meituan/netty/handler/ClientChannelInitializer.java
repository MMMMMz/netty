package com.meituan.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

/**
 * @author mazhe
 * @date 2023/8/25 20:06
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
            socketChannel.pipeline().addLast("encoder", new StringDecoder(CharsetUtil.UTF_8));
            socketChannel.pipeline().addLast(new NettyClientHandler());
    }
}
