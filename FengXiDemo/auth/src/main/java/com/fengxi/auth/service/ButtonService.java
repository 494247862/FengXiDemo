package com.fengxi.auth.service;

import com.fengxi.auth.entity.DeyiButton;
import com.fengxi.auth.vo.ButtonVO;
import com.fengxi.auth.vo.MenuButtonTreeVO;

import java.util.List;

/**
 * @author wujiuhe
 * @description: TODO
 * @title: ButtonService
 * @projectName FengXiDemo
 * @date 2023/1/30 16:51:04
 */
public interface ButtonService {
    /**
     * 新增按钮
     *
     * @param deyiButton
     * @return
     */
    String addButton(DeyiButton deyiButton);

    /**
     * 删除按钮
     *
     * @param id
     * @return
     */
    String deleteButton(Long id);

    /**
     * 获取当前用户的权限按钮
     *
     * @return
     */
    List<ButtonVO> getButtonByCurrent();

    /**
     * 通过菜单id获取按钮数据
     *
     * @param menuId
     * @return
     */
    List<DeyiButton> getButtonByMenuId(Long menuId);

    /**
     * 修改按钮
     *
     * @param deyiButton
     * @return
     */
    String updateButton(DeyiButton deyiButton);

    /**
     * 获取菜单按钮树结构
     *
     * @return
     */
    List<MenuButtonTreeVO> getMenuButtonTree();

}
