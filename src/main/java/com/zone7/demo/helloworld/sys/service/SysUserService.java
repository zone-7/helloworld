package com.zone7.demo.helloworld.sys.service;

import com.zone7.demo.helloworld.sys.pojo.SysUser;
import com.zone7.demo.helloworld.sys.vo.SysUserVo;

import java.util.List;

/**
 * SysUserService
 *
 * @author: zone7
 * @time: 2019.02.17
 */
public interface SysUserService {

    /**
     * 保存
     * @param userVo
     */
    SysUserVo save(SysUserVo userVo);

    /**
     * 修改
     * @param userVo
     */
    SysUserVo update(SysUserVo userVo);

    /**
     * 根据用户ID查找
     * @param id
     * @return
     */
    SysUserVo findById(Integer id);

    /**
     * 根据用户名模糊查找
     * @param name
     * @return
     */
    List<SysUserVo> findByName(String name);

    /**
     * 删除用户
     * @param id
     */
    void delete(Integer id);




}
