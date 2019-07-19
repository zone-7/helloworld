package com.zone7.demo.helloworld.config.mybatis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * MyBatisMapperScannerConfig
 * mybatis mapper扫描配置
 *
 * @author: zone7
 * @time: 2019.01.02
 */
@Configuration
public class MyBatisMapperScannerConfig {

    /**
     * 配置mybatis通用mapper
     *
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        //扫描该路径下的mapper
        mapperScannerConfigurer.setBasePackage("com.zone7.demo.helloworld.sys.dao, com.zone7.demo.helloworld..*.dao");
        Properties properties = new Properties();
        //通用mapper
        properties.setProperty("mappers", "com.zone7.demo.helloworld.commons.mapper.BaseMapper");
        properties.setProperty("notEmpty", "false");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }


}
