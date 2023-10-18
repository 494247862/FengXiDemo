package com.fengxi.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fengxi.auth.dto.UserPageDTO;
import com.fengxi.auth.entity.DeyiUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户mapper
 *
 * @author wujiuhe
 * @description: 用户相关Mapper
 * @title: UserMapper
 * @projectName demo
 * @date 2023/1/8 16:18:41
 */
@Mapper
public interface UserMapper extends BaseMapper<DeyiUser> {
    /**
     * 分页查询用户数据
     *
     * @param query
     * @return
     */
    List<DeyiUser> queryUserData(UserPageDTO query);

    /**
     * 查询页数
     * @param query
     * @return
     */
    Integer queryUserCount(UserPageDTO query);
}
