package com.zone7.demo.helloworld;

import com.zone7.demo.helloworld.sys.service.SysUserService;
import com.zone7.demo.helloworld.sys.vo.SysUserVo;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysuserServiceTests {
	@Autowired
	private SysUserService sysUserService;

	@Ignore("not ready yet")
	@Test
	public void testFindByName() {
		List<SysUserVo> users = sysUserService.findByName("zone7");
		System.out.println("查询到用户数："+users.size());

	}
	@Test
	public void testSave() {
		SysUserVo user = new SysUserVo();
		user.setName("testuser");
		user.setPassword("123");
		user.setPhone("111111111111");
		sysUserService.save(user);
		List<SysUserVo> users = sysUserService.findByName("testuser");
		Assert.notEmpty(users,"保存失败");
	}


}
