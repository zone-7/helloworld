package com.zone7.demo.helloworld.sys.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
* Created by Mybatis Generator 2019/06/18
*/
@Getter
@Setter
@ToString
public class SysUserVo implements Serializable{
    private Integer id;

    private String name;

    private String password;

    private String phone;

    private Integer score;

    private Integer star;

    private String department;
}