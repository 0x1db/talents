package com.wangyu.talents;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 启动类
 *
 * @author wangyu
 * @Date 2019/7/8 22:00
 */
@SpringBootApplication
@MapperScan("com.wangyu.talents.**.mapper")
public class TalentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TalentsApplication.class, args);
    }

}
