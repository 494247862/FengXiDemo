package com.fengxi.auth.service;

import com.fengxi.auth.dto.UserPageDTO;
import com.fengxi.auth.entity.DeyiUser;
import com.fengxi.auth.vo.ResultPageVO;

import java.util.List;

/**
 * @author wujiuhe
 * @description: TODO
 * @title: LoginService
 * @projectName demo
 * @date 2023/1/22 2:22:07
 */
public interface LoginService {

    /**
     * 登录
     *
     * @param user
     * @return
     */
    public DeyiUser login(DeyiUser user);

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    public String addUser(DeyiUser user);

    /**
     * 登出
     *
     * @return
     */
    public String logout();

    /**
     * 获取当前用户信息
     *
     * @return
     */
    DeyiUser getCurrentUser();

    /**
     * 获取所有用户信息
     *
     * @return
     */
    List<DeyiUser> getAllUser();

    /**
     * 更新用户信息
     *
     * @return
     */
    String updateUser(DeyiUser data);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    String deleteUser(Long id);

    /**
     * 查询用户数据
     *
     * @param query
     * @return
     */
    ResultPageVO<DeyiUser> queryUserData(UserPageDTO query);

    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    String updatePassword(DeyiUser user);
}
