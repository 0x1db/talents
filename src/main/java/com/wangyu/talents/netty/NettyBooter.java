package com.wangyu.talents.netty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * netty 服务端监听启动
 *
 * @author wangyu
 * @date 2020/3/21 17:37
 */
@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {

  @Value("${netty.port}")
  private int port;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (event.getApplicationContext().getParent() == null) {
      try {
        NettyServer.getInstance().bind(port);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
