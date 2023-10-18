package com.fengxi.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fengxi.auth.dto.RolePageDTO;
import com.fengxi.auth.entity.DeyiRole;
import com.fengxi.auth.entity.DeyiUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色mapper
 *
 * @author wujiuhe
 * @description: TODO
 * @title: RoleMapper
 * @projectName FengXiDemo
 * @date 2023/1/28 17:36:56
 */
@Mapper
public interface RoleMapper extends BaseMapper<DeyiRole> {
    /**
     * 分页获取角色数据
     *
     * @param rolePageDTO
     * @return
     */
    List<DeyiRole> QueryRolePage(RolePageDTO rolePageDTO);

    /**
     * 获取条数
     *
     * @param rolePageDTO
     * @return
     */
    Integer getCount(RolePageDTO rolePageDTO);

    /**
     * 通过角色编号获取用户
     *
     * @param code
     * @return
     */
    List<DeyiUser> getUserByCode(String code);
}
