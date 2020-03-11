package com.wangyu.talents.web;

import com.wangyu.talents.configuration.MqttProducerConfig.MsgWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyu
 * @date 2019/11/29 0:46
 */
@RestController
@RequestMapping("/mqtt")
public class MessageController {


  @Autowired
  MsgWriter msgWriter;

  @PostMapping(value = "/sendMsg")
  public String sendMsg(@RequestParam("message") String message) {
    msgWriter.write(message);
    msgWriter.write("topic2",message);
    return "success";
  }
}
