package com.zone7.demo.helloworld.sys.service.impl;

import com.mongodb.client.gridfs.GridFSBucket;

import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.zone7.demo.helloworld.config.exception.GlobalException;
import com.zone7.demo.helloworld.sys.pojo.SysUser;
import com.zone7.demo.helloworld.sys.service.MongoDBService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 描述:
 * mongo
 *
 * @author zone7
 * @date 2018/8/9 10:24
 */
@Service
public class MongoDBServiceImpl implements MongoDBService{
    private static final Logger logger = LoggerFactory.getLogger(MongoDBServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;
    @Autowired
    private GridFsTemplate gridFsTemplate;

    /**
     * 保存对象
     *
     * @param user
     * @return
     */
    public void saveObj(SysUser user) {
        logger.info("--------------------->[MongoDB save start]");
        mongoTemplate.save(user);

    }


    /**
     * 查询所有
     *
     * @return
     */
    public List<SysUser> findAll() {
        logger.info("--------------------->[MongoDB find start]");
        return mongoTemplate.findAll(SysUser.class);
    }

    /***
     * 根据id查询
     * @param id
     * @return
     */
    public SysUser getById(String id) {
        logger.info("--------------------->[MongoDB find start]");
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, SysUser.class);
    }

    /**
     * 根据名称查询
     *
     * @param username
     * @return
     */
    public SysUser getBookByName(String username) {
        logger.info("--------------------->[MongoDB find start]");
        Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query, SysUser.class);
    }

    /**
     * 更新对象
     *
     * @param user
     * @return
     */
    public void update(SysUser user) {
        logger.info("--------------------->[MongoDB update start]");
        Query query = new Query(Criteria.where("_id").is(user.getId()));
        Update update = new Update().set("password", user.getPassword())
                .set("name", user.getName())
                .set("updateTime", new Date());

        //updateFirst 更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query, update, SysUser.class);
        //updateMulti 更新查询返回结果集的全部
//        mongoTemplate.updateMulti(query,update,SysUser.class);
        //upsert 更新对象不存在则去添加
//        mongoTemplate.upsert(query,update,SysUser.class);

    }

    /***
     * 删除对象
     * @param user
     * @return
     */
    public void delete(SysUser user) {
        logger.info("--------------------->[MongoDB delete start]");
        mongoTemplate.remove(user);

    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    public void deleteById(String id) {
        logger.info("--------------------->[MongoDB delete start]");
        //findOne
        SysUser book = getById(id);
        //delete
        delete(book);
    }


    /**
     * 保存文件
     * @param input
     * @param name
     */
    public String saveFile(byte[] input,String name){
        ObjectId objectId=gridFsTemplate.store(new ByteArrayInputStream(input) ,name);
        String s = objectId.toString();
        return s;
    }

    /**
     * 加载文件
     *
     * @param id
     * @return
     */
    public byte[] loadFile(String id)   {
        try {
            Query query = Query.query(Criteria.where("_id").is(id));
            GridFSFile gfsFile = gridFsTemplate.findOne(query);

            String filename = gfsFile.getFilename();

            logger.info("加载mongodb gf 文件名："+filename);

            //打开流下载对象
            GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(gfsFile.getObjectId());
            //获取流对象
            GridFsResource gridFsResource = new GridFsResource(gfsFile, downloadStream);

            return StreamUtils.copyToByteArray(gridFsResource.getInputStream());
        }catch(IOException e){
            logger.error("读取mongodb文件错误",e);
            throw new GlobalException("读取mongodb文件错误",e);
        }
    }


    @Override
    public byte[] loadFileByName(String name) {
        try {
            Query query = Query.query(Criteria.where("filename").is(name));
            GridFSFile gfsFile = gridFsTemplate.findOne(query);

            String filename = gfsFile.getFilename();

            logger.info("加载mongodb gf 文件名："+filename);

            //打开流下载对象
            GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(gfsFile.getObjectId());
            //获取流对象
            GridFsResource gridFsResource = new GridFsResource(gfsFile, downloadStream);

            return StreamUtils.copyToByteArray(gridFsResource.getInputStream());
        }catch(IOException e){
            logger.error("读取mongodb文件错误",e);
            throw new GlobalException("读取mongodb文件错误",e);
        }
    }
}