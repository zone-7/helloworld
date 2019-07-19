package com.zone7.demo.helloworld.sys.controller;

import com.zone7.demo.helloworld.commons.response.ResponseData;
import com.zone7.demo.helloworld.sys.service.RabbitService;
import com.zone7.demo.helloworld.sys.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zone7
 * @Date: 2019/06/17
 * @Version 1.0
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitController {
    @Autowired
    private RabbitService rabbitService;

    @GetMapping("/send/{message}")
    public ResponseData send(@PathVariable String message ){
        rabbitService.sendMessage(message);

        return ResponseData.successMessage("发送消息： "+message+"成功");
    }

}