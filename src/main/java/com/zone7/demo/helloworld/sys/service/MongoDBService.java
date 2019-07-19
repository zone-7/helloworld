package com.zone7.demo.helloworld.sys.service;


import com.zone7.demo.helloworld.sys.pojo.SysUser;
import org.bson.types.ObjectId;

import java.io.InputStream;
import java.util.List;

/**
 * SysUserService
 *
 * @author: zone7
 * @time: 2019.02.17
 */
public interface MongoDBService {


    public void saveObj(SysUser user);


    /**
     * 查询所有
     *
     * @return
     */
    public List<SysUser> findAll() ;

    /***
     * 根据id查询
     * @param id
     * @return
     */
    public SysUser getById(String id);

    /**
     * 根据名称查询
     *
     * @param username
     * @return
     */
    public SysUser getBookByName(String username) ;

    /**
     * 更新对象
     *
     * @param user
     * @return
     */
    public void update(SysUser user) ;

    /***
     * 删除对象
     * @param user
     * @return
     */
    public void delete(SysUser user);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    public void deleteById(String id);

    /**
     * 保存文件
     * @param input
     * @param name
     * @return
     */
    public String saveFile(byte[] input, String name);

    /**
     * 加载文件
     *
     * @param id
     * @return
     */
    public byte[] loadFile(String id);

    /**
     * 使用文件名加载文件
     *
     * @param name
     * @return
     */
    public byte[] loadFileByName(String name);
}
