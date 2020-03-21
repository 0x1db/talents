package com.wangyu.talents.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * I/O数据读写处理类
 *
 * @author wangyu
 * @date 2020/3/21 18:03
 */
public class BootNettyChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {

  private Logger LOG = LoggerFactory.getLogger(this.getClass());

  /**
   * 客户端与服务端第一次建立连接时 执行
   */
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);
    ctx.channel().read();
    InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
    String clientIp = insocket.getAddress().getHostAddress();
    int port = insocket.getPort();
    //此处不能使用ctx.close()，否则客户端始终无法与服务端建立连接
    LOG.info("client 首次建立连接，ip:{},port:{}", clientIp,port);
  }

  /**
   * 客户端与服务端 断连时 执行
   */
  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    super.channelInactive(ctx);
    InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
    String clientIp = insocket.getAddress().getHostAddress();
    int port = insocket.getPort();
    //断开连接时，必须关闭，否则造成资源浪费，并发量很大情况下可能造成宕机
    ctx.close();
    LOG.info("client 断开连接，ip:{},port{}", clientIp,port);
  }

  /**
   * 从客户端收到新的数据时，这个方法会在收到消息时被调用
   */
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    LOG.info("channelRead:read msg:{}", msg.toString());
    ChannelId id = ctx.channel().id();
    LOG.info("++++++++++++++++++++++++++++"+id);
    //回应客户端
    ctx.write("I got it");
  }

  /**
   * 从客户端收到新的数据、读取完成时调用
   */
  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    LOG.info("channel Read Complete!");
    ctx.flush();
  }

  /**
   * 服务端当read超时, 会调用这个方法
   */
  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    super.userEventTriggered(ctx, evt);
    InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
    String clientIp = insocket.getAddress().getHostAddress();
    ctx.close();//超时时断开连接
    LOG.info("userEventTriggered: {}", clientIp);
  }

  /**
   * 当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时
   */
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    LOG.info("exceptionCaught");
    cause.printStackTrace();
    ctx.close();//抛出异常，断开与客户端的连接
  }
}
