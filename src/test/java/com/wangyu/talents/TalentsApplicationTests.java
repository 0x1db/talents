package com.wangyu.talents;

import com.wangyu.talents.configuration.MqttProducerConfig.MsgWriter;
import com.wangyu.talents.service.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class TalentsApplicationTests {

    @Autowired
    private Sender sender;

/*    @Test
    public void contextLoads() {
        this.sender.send("Hello RabbitMQ");
    }*/

    @Autowired
    MsgWriter msgWriter;

    @Test
    public void sendMsg() {
        String message = "hello ";
        msgWriter.write(message);
        msgWriter.write("topic2",message);
    }
}
