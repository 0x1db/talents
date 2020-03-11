package com.wangyu.talents.configuration;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author wangyu
 * @date 2019/11/29 1:14
 */
@IntegrationComponentScan
@Configuration
public class MqttProducerConfig {

  private static final byte[] WILL_DATA;

  static {
    WILL_DATA = "offline".getBytes();
  }

  /**
   * 订阅的bean名称
   */
  public static final String CHANNEL_NAME_IN = "mqttInboundChannel";
  /**
   * 发布的bean名称
   */
  public static final String CHANNEL_NAME_OUT = "mqttOutboundChannel";

  @Value("${mqtt.username}")
  private String username;

  @Value("${mqtt.password}")
  private String password;

  @Value("${mqtt.url}")
  private String url;

  @Value("${mqtt.producer.clientId}")
  private String producerClientId;

  @Value("${mqtt.producer.defaultTopic}")
  private String producerDefaultTopic;

  @Value("${mqtt.consumer.clientId}")
  private String consumerClientId;

  @Value("${mqtt.consumer.defaultTopic}")
  private String consumerDefaultTopic;


  /**
   * MQTT连接器选项
   *
   * @return {@link org.eclipse.paho.client.mqttv3.MqttConnectOptions}
   */
  @Bean
  public MqttConnectOptions getMqttConnectOptions() {
    MqttConnectOptions options = new MqttConnectOptions();
    // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
    // 这里设置为true表示每次连接到服务器都以新的身份连接
    options.setCleanSession(true);
    // 设置连接的用户名
    options.setUserName(username);
    // 设置连接的密码
    options.setPassword(password.toCharArray());
    options.setServerURIs(StringUtils.split(url, ","));
    // 设置超时时间 单位为秒
    options.setConnectionTimeout(10);
    // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线，但这个方法并没有重连的机制
    options.setKeepAliveInterval(20);
    // 设置“遗嘱”消息的话题，若客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息。
    options.setWill("willTopic", WILL_DATA, 2, false);
    return options;
  }


  /**
   * MQTT客户端
   *
   * @return {@link org.springframework.integration.mqtt.core.MqttPahoClientFactory}
   */
  @Bean
  public MqttPahoClientFactory mqttClientFactory() {
    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    factory.setConnectionOptions(getMqttConnectOptions());
    return factory;
  }

  /**
   * MQTT信息通道（生产者）
   *
   * @return {@link org.springframework.messaging.MessageChannel}
   */
  @Bean(name = CHANNEL_NAME_OUT)
  public MessageChannel mqttOutboundChannel() {
    return new DirectChannel();
  }

  /**
   * MQTT消息处理器（生产者）
   *
   * @return {@link org.springframework.messaging.MessageHandler}
   */
  @Bean
  @ServiceActivator(inputChannel = CHANNEL_NAME_OUT)
  public MessageHandler mqttOutbound() {
    MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
        producerClientId,
        mqttClientFactory());
    messageHandler.setAsync(true);
    messageHandler.setDefaultTopic(producerDefaultTopic);
    return messageHandler;
  }

  /**
   * 消息推送
   */
  @Component
  @MessagingGateway(defaultRequestChannel = CHANNEL_NAME_OUT)
  public interface MsgWriter {

    void write(String note);

    void write(@Header(MqttHeaders.TOPIC) String topic, String payload);

    void write(@Header(MqttHeaders.TOPIC) String topic, String payload, int qos);
  }
}
