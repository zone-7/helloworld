package com.zone7.demo.helloworld.config.mongodb;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;

/**
 * MongoDB配置类
 *
 * @author zone7
 * @date 2019/5/7
 */
@Configuration
public class MongoDbConfig {

    @Autowired
    private MongoDbFactory mongoDbFactory;

    @Bean
    public GridFSBucket gridFSBucket() {
        return GridFSBuckets.create(mongoDbFactory.getDb());
    }

}
