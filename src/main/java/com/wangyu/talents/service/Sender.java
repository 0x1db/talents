package com.wangyu.talents.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangyu
 * @Date 2019/9/2 23:32
 * @Desc 消息发送者
 */
public class Sender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * @Desc 发送消息的方法
     * @Author wangyu
     * @Date 2019/9/2 23:46
     * @Param msg 消息内容
     * @Return
     */
    public void send(String msg) {
        //向消息队列发送消息
        //参数一，消息队列的名字
        //参数二，消息内容
        this.amqpTemplate.convertAndSend("hello-queue", msg);
    }
}
