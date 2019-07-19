package com.zone7.demo.helloworld.sys.controller;

import com.zone7.demo.helloworld.commons.response.ResponseData;
import com.zone7.demo.helloworld.sys.service.RedisService;
import com.zone7.demo.helloworld.sys.service.SysUserService;
import com.zone7.demo.helloworld.sys.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zone7
 * @Date: 2019/06/17
 * @Version 1.0
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisService redisService;

    @GetMapping("/put/{key}/{value}")
    public ResponseData put(@PathVariable String key,@PathVariable String value){
        redisService.put(key,value);

        return ResponseData.successMessage("put "+key+"成功");
    }


    @GetMapping("/get/{key}")
    @ResponseBody
    public ResponseData findByName(@PathVariable String key) {
        String value = redisService.get(key);
        return ResponseData.success(value);
    }

}