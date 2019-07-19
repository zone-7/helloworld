package com.zone7.demo.helloworld.sys.controller;

import com.zone7.demo.helloworld.commons.response.ResponseData;
import com.zone7.demo.helloworld.sys.service.SysUserService;
import com.zone7.demo.helloworld.sys.vo.SysUserVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SysUserController
 * 系统管理->用户管理 模块的控制层
 * @author: zone7
 * @time: 2019.02.17
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 保存
     * @param userVo
     * @return
     */
    @ApiOperation(value = "保存用户信息", notes = "保存用户信息：包括用户名、密码、电话等内容")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public ResponseData save(SysUserVo userVo) {
        userVo= sysUserService.save(userVo);
        return ResponseData.success(userVo);
    }


    /**
     * 更新
     * @param userVo
     * @return
     */
    @ApiOperation(value = "更新用户信息", notes = "根据用户ID更新用户信息")
    @RequestMapping(value = "/update")
    public ResponseData updateDept(SysUserVo userVo) {
        SysUserVo vo = sysUserService.update(userVo);
        return ResponseData.success(vo);
    }

    /**
     * 查找
     * @param name
     * @return
     */
    @ApiOperation(value = "用户查询", notes = "根据用户名模糊查询用户信息")
    @GetMapping("/findByName")
    public ResponseData findByName(@RequestParam(value = "name") String name) {
        List<SysUserVo> result = sysUserService.findByName(name);
        return ResponseData.success(result);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    @ApiOperation(value = "用户查询", notes = "根据用户id查询用户信息")
    @GetMapping("/findById")
    public ResponseData findByName(@RequestParam(value = "id") Integer id) {
         SysUserVo result = sysUserService.findById(id);
        return ResponseData.success(result);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "用户删除", notes = "根据用户ID删除用户信息")
    @RequestMapping("/delete")
    public ResponseData delete(Integer id) {
        sysUserService.delete(id);
        return ResponseData.successMessage("删除用户成功");
    }


}
