package com.wangyu.talents.service;

/**
 * @author wangyu
 * @date 2020/3/25 23:45
 */
public class AccessRecordsStrategy implements TopicStrategy {

  @Override
  public void receiveMessage(String topic, String message) {
    System.out.println("===========通行记录主题订阅内容：" + message);
  }
}
