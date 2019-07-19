package com.zone7.demo.helloworld.sys.controller;

import com.zone7.demo.helloworld.commons.response.ResponseData;
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
@RequestMapping("/test")
public class TestController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/show/{name}")
    public String show(@PathVariable String name){
        return "hi, " +name +" , 你好！我是 zone7";
    }
    /**
     * 查找
     * @param name
     * @return
     */
    @GetMapping("/findByName/{name}")
    @ResponseBody
    public ResponseData findByName(@PathVariable String name) {
        List<SysUserVo> result = sysUserService.findByName(name);
        return ResponseData.success(result);
    }

}