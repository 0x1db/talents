package com.wangyu.talents.service;

/**
 * 订阅主题策略接口
 *
 * @author wangyu
 * @date 2020/3/25 23:28
 */
public interface TopicStrategy {

  /**
   * 接收订阅内容
   */
  void receiveMessage(String topic, String message);
}
