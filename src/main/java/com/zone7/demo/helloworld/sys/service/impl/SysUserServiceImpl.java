package com.zone7.demo.helloworld.sys.service.impl;

import com.zone7.demo.helloworld.commons.exception.AddOPException;
import com.zone7.demo.helloworld.commons.exception.DeleteOPException;
import com.zone7.demo.helloworld.commons.exception.UpdateOPException;
import com.zone7.demo.helloworld.sys.dao.SysUserMapper;
import com.zone7.demo.helloworld.sys.pojo.SysUser;
import com.zone7.demo.helloworld.sys.service.SysUserService;
import com.zone7.demo.helloworld.sys.vo.SysUserVo;
import com.zone7.demo.helloworld.utils.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * SysUserServiceImpl
 *
 * @author: zone7
 * @time: 2019.02.17
 */
@Service
@CacheConfig(cacheNames = "users")
public class SysUserServiceImpl implements SysUserService {

    @Value("${system.default.password}")
    private String defaultPassword;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    @Override
    @Transactional(rollbackFor = AddOPException.class)
    @CachePut
    public SysUserVo save(SysUserVo userVo) {
        try {


            String password = defaultPassword;
            String md5Password = MD5Util.encode(password);
            SysUser sysUser =  SysUser.builder()
                    .name(userVo.getName()).password(md5Password)
                    .phone(userVo.getPhone())
                    .department(userVo.getDepartment())
                    .build();


            // 存储用户信息
            sysUserMapper.insertSelective(sysUser);

            SysUserVo vo=new SysUserVo();
            BeanUtils.copyProperties(sysUser,vo);

            return vo;

        } catch (Exception e) {

            throw new AddOPException("新增用户操作出错，错误原因: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = UpdateOPException.class)
    @CachePut
    public SysUserVo update(SysUserVo userVo) {
        try {

            SysUser after = SysUser.builder()
                    .id(userVo.getId())
                    .name(userVo.getName())
                    .phone(userVo.getPhone())
                    .department(userVo.getDepartment()).build();

            sysUserMapper.updateByPrimaryKeySelective(after);

            SysUserVo vo=new SysUserVo();
            BeanUtils.copyProperties(after,vo);

            return vo;
        } catch (Exception e) {

            throw new UpdateOPException("更新用户操作出错，错误原因: " + e.getMessage());
        }
    }

    @Override
    @Cacheable
    public SysUserVo findById(Integer id) {

          System.out.println("从数据库中查找用户");
          SysUser user = sysUserMapper.selectByPrimaryKey(id);
          SysUserVo vo=new SysUserVo();
          BeanUtils.copyProperties(user,vo);
          return vo;
    }


    @Override
    public List<SysUserVo> findByName( String name) {
        List<SysUser> users = sysUserMapper.findByName(name);

        List<SysUserVo> userList = new ArrayList<SysUserVo>();
        users.forEach(sysUser -> {
            // 查询角色信息
            SysUserVo vo=new SysUserVo();
            BeanUtils.copyProperties(sysUser,vo);
            userList.add(vo);
        });
        return userList;
    }

    @Override
    @Transactional(rollbackFor = DeleteOPException.class)
    @CacheEvict
    public void delete(Integer id) {
        try {
            sysUserMapper.deleteByPrimaryKey(id);

        } catch (Exception e) {
            e.printStackTrace();
            throw new DeleteOPException("删除用户操作出错，错误原因: " + e.getMessage());
        }
    }

}
