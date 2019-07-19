package com.zone7.demo.helloworld.sys.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* Created by Mybatis Generator 2019/06/18
*/
@Builder
@Getter
@Setter
@ToString
public class SysUser {
    private Integer id;

    private String name;

    private String password;

    private String phone;

    private Integer score;

    private Integer star;

    private String department;
}