package com.wangyu.talents.web;

import com.wangyu.talents.service.MyWebSocket;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @ResponseBody
    @GetMapping("/broadcast")
    public void broadcast() {
        String message = "广播测试";
        MyWebSocket.broadcast(message);
    }

    @RequestMapping("/html")
    public String getHtml() {
        System.out.println("test");
        return "test";
    }
}
