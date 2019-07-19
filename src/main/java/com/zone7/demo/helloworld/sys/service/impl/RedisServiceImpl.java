package com.zone7.demo.helloworld.sys.service.impl;

import com.zone7.demo.helloworld.sys.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @ClassName: RedisServiceImpl
 * @Description: redis 案例测试
 * @Author: zgq
 * @Date: 2019/6/19 09:16
 * @Version: 1.0
 */

@Service
public class RedisServiceImpl implements RedisService {
  @Autowired
  private RedisTemplate redisTemplate;
  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  /**
   * 写入
   * @param key
   * @param value
   */
  @Override
  public void put(String key, String value) {
    //redisTemplate.opsForList().leftPush(key, value);
    stringRedisTemplate.opsForValue().set(key,value);
  }

  /**
   * 读取
   * @param key
   * @return
   */
  @Override
  public String get(String key) {
    return stringRedisTemplate.opsForValue().get(key);

  }

}
