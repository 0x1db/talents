package com.wangyu.talents.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.nio.charset.Charset;

/**
 * 通道初始化
 *
 * @author wangyu
 * @date 2020/3/21 17:56
 */
public class BootNettyChannelInitializer<SocketChannel> extends ChannelInitializer<Channel> {

  @Override
  protected void initChannel(Channel channel) throws Exception {
    // ChannelOutboundHandler，依照逆序执行
    channel.pipeline().addLast("encoder", new StringEncoder(Charset.forName("GBK")));

    // ChannelOutboundHandler，依照逆序执行
    channel.pipeline().addLast("decoder", new StringDecoder(Charset.forName("UTF-8")));

    // 自定义ChannelInboundHandlerAdapter
    channel.pipeline().addLast(new BootNettyChannelInboundHandlerAdapter());
  }
}
