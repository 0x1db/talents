package com.wangyu.talents.service;

/**
 * @author wangyu
 * @date 2020/3/25 23:46
 */
public class TopicContext {

  private TopicStrategy topicStrategy;

  public void receiveMessage(String topic,String message){
    topicStrategy = TopicStrategyFactory.getInstance().creator(topic);
    topicStrategy.receiveMessage(topic,message);
  }

  public TopicStrategy getTopicStrategy() {
    return topicStrategy;
  }

  public void setTopicStrategy(TopicStrategy topicStrategy) {
    this.topicStrategy = topicStrategy;
  }
}
