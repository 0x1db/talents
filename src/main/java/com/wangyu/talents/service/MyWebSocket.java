package com.wangyu.talents.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket")
public class MyWebSocket {

    /**
     * slf4j
     */
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    //保存所有在线的websocket连接
    private static Map<String, MyWebSocket> webSocketMap = new ConcurrentHashMap<>();

    //记录当前在线数目
    private static int count = 1;

    //当前连接（每个websocket连入都会创建一个MyWebSocket实例
    private Session session;

    //获取在线连接数目
    public static int getCount() {
        return count;
    }

    //新增count，使用synchronized确保线程安全
    public static synchronized void addCount() {
        MyWebSocket.count++;
    }

    //减少count
    public static synchronized void reduceCount() {
        MyWebSocket.count--;
    }

    /**
     * @Author wangyu
     * @Date 2019/10/20 21:17
     * @Desc 建立连接
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketMap.put(session.getId(), this);
        addCount();
        LOG.info("新的连接加入：{}", session.getId());
    }

    /**
     * @Author wangyu
     * @Date 2019/10/20 21:25
     * @Desc 接受消息
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        LOG.info("收到客户端消息：[{}]", message);
        try {
            this.sendMessage(message);
        } catch (IOException e) {
            LOG.error("Error:[{}]", e);
        }
    }

    @OnError
    public void onError(Throwable error, Session session) {
        LOG.info("发生错误{},{}", session.getId(), error.getMessage());
    }

    //处理连接关闭
    @OnClose
    public void onClose() {
        webSocketMap.remove(this.session.getId());
        reduceCount();
        LOG.info("连接关闭:{}", this.session.getId());
    }

    /**
     * @Author wangyu
     * @Date 2019/10/20 21:35
     * @Desc 群发
     */
    public static void broadcast(String message) {
        MyWebSocket.webSocketMap.forEach((k, v) -> {
            try {
                v.sendMessage(message);
            } catch (IOException e) {

            }
        });
    }

    /**
     * @Author wangyu
     * @Date 2019/10/20 21:24
     * @Desc 发送消息
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


}
