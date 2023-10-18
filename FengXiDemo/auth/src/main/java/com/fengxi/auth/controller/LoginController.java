package com.fengxi.auth.controller;

import com.fengxi.auth.dto.UserPageDTO;
import com.fengxi.auth.entity.DeyiUser;
import com.fengxi.auth.service.LoginService;
import com.fengxi.auth.service.impl.WebSocketServer;
import com.fengxi.auth.vo.ResultPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录控制器
 *
 * @author wujiuhe
 * @description: TODO
 * @title: LoginController
 * @projectName demo
 * @date 2023/1/22 2:12:09
 */
@RestController
@RequestMapping("/user")
@Api(tags = "登录控制器")
@Log4j2
public class LoginController {

    @Resource
    LoginService loginService;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public DeyiUser login(@RequestBody DeyiUser user) {
        return loginService.login(user);
    }


    @PostMapping("/addUser")
    @ApiOperation(value = "添加账号")
    public String addUser(@RequestBody DeyiUser user) {
        return loginService.addUser(user);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "登出")
    public String logout() {
        return loginService.logout();
    }

    @PostMapping("/getCurrentUser")
    @ApiOperation(value = "获取当前用户信息")
    public DeyiUser getCurrentUser() {
        return loginService.getCurrentUser();
    }

    @PostMapping("/getAllUser")
    @ApiOperation(value = "获取所有用户")
    public List<DeyiUser> getAllUser() {
        return loginService.getAllUser();
    }

    @PostMapping("/updateUser")
    @ApiOperation(value = "更新用户")
    public String updateUser(@RequestBody DeyiUser user) {
        return loginService.updateUser(user);
    }

    @DeleteMapping("/deleteUser/{id}")
    @ApiOperation(value = "删除用户")
    public String deleteUser(@ApiParam("用户id") @PathVariable Long id) {
        return loginService.deleteUser(id);
    }

    @PostMapping("/queryUserData")
    @ApiOperation(value = "查询用户数据")
    public ResultPageVO<DeyiUser> queryUserData(@RequestBody UserPageDTO query) {
        return loginService.queryUserData(query);
    }

    @PostMapping("/updatePassword")
    @ApiOperation(value = "修改密码")
    public String updatePassword(@RequestBody DeyiUser user){
        return loginService.updatePassword(user);
    }


    @GetMapping("/testSocket/{id}")
    @ApiOperation(value = "测试WebSocket")
    public String testSocket(@PathVariable String id){
        WebSocketServer.sendMessageByUserIdForRedis(id,"hahahah");
        return null;
    }
}
