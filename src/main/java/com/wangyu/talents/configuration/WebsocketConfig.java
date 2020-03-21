package com.wangyu.talents.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author wangyu
 * @Date 2019/10/20 20:37
 * @Desc websocket 配置
 */
//@Configuration
public class WebsocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
