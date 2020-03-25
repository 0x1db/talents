package com.wangyu.talents.service;

/**
 * @author wangyu
 * @date 2020/3/25 23:46
 */
public class CallBackStrategy implements TopicStrategy {

  @Override
  public void receiveMessage(String topic, String message) {
    System.out.println("===========回调主题订阅内容："+message);
  }
}
