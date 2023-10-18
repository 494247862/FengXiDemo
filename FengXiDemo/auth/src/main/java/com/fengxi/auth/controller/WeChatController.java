package com.fengxi.auth.controller;

import com.fengxi.auth.dto.GetPhoneParam;
import com.fengxi.auth.utils.AESUtil;
import com.fengxi.auth.utils.HttpRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * 微信接口控制器
 * @author wujiuhe
 * @description: TODO
 * @title: WeChatController
 * @projectName FengXiDemo
 * @date 2023/3/3 14:25:47
 */
@RestController
@RequestMapping("/wechat")
@Api(tags = "微信接口控制器")
public class WeChatController {

    @Value("${app-id}")
    String AppID;

    @Value("${app-secret}")
    String AppSecret;

    @GetMapping("/getOpenId/{code}")
    @ApiOperation(value = "获取openid")
    public String getOpenId(@PathVariable String code) throws IOException {
        String url = MessageFormat.format("https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code", AppID, AppSecret, code);
        return HttpRequestUtil.get(url);
    }

    @PostMapping("/AESDecrypt")
    @ApiOperation(value = "获取微信手机号")
    public String AESDecrypt(@RequestBody GetPhoneParam param) {
        return AESUtil.AESDecrypt(param);
    }
}
