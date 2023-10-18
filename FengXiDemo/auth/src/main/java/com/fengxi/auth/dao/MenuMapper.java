package com.fengxi.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fengxi.auth.entity.DeyiMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单Mapper
 *
 * @author wujiuhe
 * @description: TODO
 * @title: MenuMapper
 * @projectName FengXiDemo
 * @date 2023/1/28 14:19:04
 */
@Mapper
public interface MenuMapper extends BaseMapper<DeyiMenu> {
}
