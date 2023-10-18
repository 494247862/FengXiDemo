package com.fengxi.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fengxi.auth.dao.UserMapper;
import com.fengxi.auth.entity.DeyiUser;
import com.fengxi.auth.exception.BaseKnowException;
import com.fengxi.auth.vo.LoginUserVO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Security校验密码
 * @author wujiuhe
 * @description: TODO
 * @title: UserDetailsServiceImpl
 * @projectName demo
 * @date 2023/1/22 1:53:41
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        DeyiUser deyiUser = userMapper.selectOne(new LambdaQueryWrapper<DeyiUser>()
                .eq(DeyiUser::getIsDeleted, false)
                .eq(DeyiUser::getAccount, username)
        );

        if (Objects.isNull(deyiUser)) {
            throw new BaseKnowException(10000, "用户名或密码错误");
        }

        return new LoginUserVO(deyiUser);
    }
}
