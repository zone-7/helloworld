package com.zone7.demo.helloworld.sys.dao;

import com.zone7.demo.helloworld.sys.pojo.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* Created by Mybatis Generator 2019/06/18
*/
public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser entity);

    int insertSelective(SysUser entity);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser entity);

    int updateByPrimaryKey(SysUser entity);

    /**
     * 根据用户名查询
     * @param name
     * @return
     */
    List<SysUser> findByName(@Param("name") String name);

}