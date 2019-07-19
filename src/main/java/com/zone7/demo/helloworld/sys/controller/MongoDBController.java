package com.zone7.demo.helloworld.sys.controller;

import com.zone7.demo.helloworld.commons.response.ResponseData;
import com.zone7.demo.helloworld.sys.pojo.SysUser;
import com.zone7.demo.helloworld.sys.service.MongoDBService;
import com.zone7.demo.helloworld.sys.service.RabbitService;
import com.zone7.demo.helloworld.sys.vo.SysUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * mongodb 测试Controller
 * @Author: zone7
 * @Date: 2019/06/17
 * @Version 1.0
 */
@RestController
@RequestMapping("/mongo")
public class MongoDBController {
    @Autowired
    private MongoDBService mongoDBService;

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public ResponseData save(SysUserVo userVo){

        SysUser user =   SysUser.builder().build();

        BeanUtils.copyProperties(userVo,user);
        mongoDBService.saveObj(user);

        return ResponseData.successMessage("保存成功");
    }
    @RequestMapping(value = "/findAll" )
    public ResponseData save(){
        List<SysUser> users  = mongoDBService.findAll();

        return ResponseData.success(users);
    }


    @RequestMapping(value = "/saveFile" )
    public ResponseData saveFile(String content,String name){

        String id = mongoDBService.saveFile(content.getBytes(),name);

        return ResponseData.success(id);
    }

    @RequestMapping(value = "/loadFileByName" )
    public ResponseData loadFileByName( String name){

        byte[] content = mongoDBService.loadFileByName(name);

        return ResponseData.success(new String(content));
    }


}