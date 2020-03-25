package com.wangyu.talents.service;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略工厂对象
 *
 * @author wangyu
 * @date 2020/3/26 0:02
 */
public class TopicStrategyFactory {

  private static TopicStrategyFactory factory = new TopicStrategyFactory();

  public TopicStrategyFactory() {
  }

  private static Map strategyMap = new HashMap<>(16);

  static {
    strategyMap.put("face/response", new CallBackStrategy());
    strategyMap.put("face/request", new AccessRecordsStrategy());
  }

  public TopicStrategy creator(String topic) {
    return (TopicStrategy)strategyMap.get(topic);
  }

  public static TopicStrategyFactory getInstance() {
    return factory;
  }
}
