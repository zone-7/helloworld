package com.zone7.demo.helloworld;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
 
/**
 * Created by zone7
 * Date 2019/6/2
 * Description:打包测试
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SysuserControllerTests.class,SysuserServiceTests.class})
public class TestSuits {
 
    //不用写代码，只需要类顶级注解即可
}
 
