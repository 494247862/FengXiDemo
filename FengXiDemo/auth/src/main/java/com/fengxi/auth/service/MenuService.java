package com.fengxi.auth.service;

import com.fengxi.auth.dto.MenuQueryDTO;
import com.fengxi.auth.entity.DeyiMenu;
import com.fengxi.auth.vo.MenuTreeVO;

import java.util.List;

/**
 * @author wujiuhe
 * @description: TODO
 * @title: MenuService
 * @projectName FengXiDemo
 * @date 2023/1/28 14:38:49
 */
public interface MenuService {

    /**
     * 新增菜单
     *
     * @param deyiMenu
     * @return
     */
    public String addMenu(DeyiMenu deyiMenu);

    /**
     * 获取菜单树
     *
     * @return
     */
    List<MenuTreeVO> getMenuTrees();

    /**
     * 通过角色id获取菜单树
     *
     * @param roleId
     * @return
     */
    List<MenuTreeVO> getMenuTreesByRoleId(Long roleId);

    /**
     * 获取当前用户的权限菜单树
     *
     * @return
     */
    List<MenuTreeVO> getMenuTreesByCurrent();

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    String deleteMenu(Long id);

    /**
     * 查询菜单数据
     *
     * @param menuQueryDTO
     * @return
     */
    List<MenuTreeVO> QueryMenuData(MenuQueryDTO menuQueryDTO);

    /**
     * 修改菜单数据
     *
     * @param deyiMenu
     * @return
     */
    String updateMenu(DeyiMenu deyiMenu);

    /**
     * 获取当前用户菜单
     *
     * @return
     */
    List<DeyiMenu> getMenuByCurrent();
}
