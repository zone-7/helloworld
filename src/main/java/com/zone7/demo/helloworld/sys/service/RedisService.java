package com.zone7.demo.helloworld.sys.service;

import com.zone7.demo.helloworld.sys.vo.SysUserVo;

import java.util.List;

/**
 * SysUserService
 *
 * @author: zone7
 * @time: 2019.02.17
 */
public interface RedisService {


    void put(String key,String value);


    String get(String key);


}
