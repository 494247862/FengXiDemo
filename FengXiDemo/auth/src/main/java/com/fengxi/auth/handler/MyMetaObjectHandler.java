package com.fengxi.auth.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.fengxi.auth.entity.DeyiUser;
import com.fengxi.auth.service.LoginService;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * mybatis字段自动填充handler
 *
 * @author wujiuhe
 * @description: TODO
 * @title: MyMetaObjectHandler
 * @projectName demo
 * @date 2023/1/16 16:21:56
 */
@Component
@Log4j2
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Lazy
    @Resource
    LoginService loginService;

    @Override
    public void insertFill(MetaObject metaObject) {

        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("isDeleted", false, metaObject);

        try {
            DeyiUser currentUser = loginService.getCurrentUser();
            this.setFieldValByName("createUserId", currentUser.getId(), metaObject);
        }catch (Exception e) {
//            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        try {
            DeyiUser currentUser = loginService.getCurrentUser();
            this.setFieldValByName("updateUserId", currentUser.getId(), metaObject);
        }catch (Exception e) {
//            log.error(e.getMessage(), e);
        }
    }
}
