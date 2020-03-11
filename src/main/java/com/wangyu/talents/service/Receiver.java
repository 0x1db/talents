package com.wangyu.talents.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author wangyu
 * @Date 2019/9/2 23:47
 * @Desc 消息接收者
 */
public class Receiver {

    /**
     * @Desc 接收消息的方法
     * @Author wangyu
     * @Date 2019/9/2 23:51
     * @Param
     * @Return
     */
    @RabbitListener(queues = "hello-queue")
    public void process(String msg) {
        System.out.println("receiver: " + msg);
    }
}
