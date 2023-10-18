package com.fengxi.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fengxi.auth.dao.ButtonMapper;
import com.fengxi.auth.dao.MenuMapper;
import com.fengxi.auth.dao.RoleMapper;
import com.fengxi.auth.entity.DeyiButton;
import com.fengxi.auth.entity.DeyiMenu;
import com.fengxi.auth.entity.DeyiRole;
import com.fengxi.auth.entity.DeyiUser;
import com.fengxi.auth.exception.BaseKnowException;
import com.fengxi.auth.service.ButtonService;
import com.fengxi.auth.service.LoginService;
import com.fengxi.auth.vo.ButtonVO;
import com.fengxi.auth.vo.MenuButtonTreeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wujiuhe
 * @description: TODO
 * @title: ButtonServiceImpl
 * @projectName FengXiDemo
 * @date 2023/1/30 16:51:38
 */
@Service
public class ButtonServiceImpl implements ButtonService {

    @Resource
    ButtonMapper buttonMapper;

    @Resource
    LoginService loginService;

    @Resource
    RoleMapper roleMapper;

    @Resource
    MenuMapper menuMapper;

    @Override
    public String addButton(DeyiButton deyiButton) {
        List<DeyiButton> deyiButtons = buttonMapper.selectList(new LambdaQueryWrapper<DeyiButton>()
                .eq(DeyiButton::getIsDeleted, false)
                .eq(DeyiButton::getButtonCode, deyiButton.getButtonCode())
                .eq(DeyiButton::getMenuId, deyiButton.getMenuId())
        );
        if (deyiButtons.size() > 0)
            throw new BaseKnowException(10000, "该菜单下已存在当前编号的按钮");

        buttonMapper.insert(deyiButton);
        return "Success";
    }

    @Override
    public String deleteButton(Long id) {
        buttonMapper.update(new DeyiButton(), new LambdaUpdateWrapper<DeyiButton>()
                .set(DeyiButton::getIsDeleted, true)
                .eq(DeyiButton::getId, id)
        );

        return "Success";
    }

    @Override
    public List<ButtonVO> getButtonByCurrent() {
        DeyiUser currentUser = loginService.getCurrentUser();
        if (Objects.isNull(currentUser.getRoleIds())) {
            return new ArrayList<>();
        }
        List<String> roleIds = Arrays.stream(currentUser.getRoleIds().split(",")).collect(Collectors.toList());
        List<ButtonVO> buttonVOS = new ArrayList<>();
        // 获取用户的角色
        for (String roleId : roleIds) {
            // 角色中找按钮
            DeyiRole deyiRole = roleMapper.selectOne(new LambdaQueryWrapper<DeyiRole>()
                    .eq(DeyiRole::getIsDeleted, false)
                    .eq(DeyiRole::getId, roleId)
            );
            if (Objects.isNull(deyiRole.getButtonIds()))
                continue;

            // 按钮信息封装
            List<String> buttonIds = Arrays.stream(deyiRole.getButtonIds().split(",")).collect(Collectors.toList());
            for (String buttonId : buttonIds) {
                DeyiButton deyiButton = buttonMapper.selectOne(new LambdaQueryWrapper<DeyiButton>()
                        .eq(DeyiButton::getIsDeleted, false)
                        .eq(DeyiButton::getId, buttonId)
                );
                if (Objects.isNull(deyiButton))
                    continue;
                ButtonVO buttonVO = new ButtonVO();
                buttonVO.setId(deyiButton.getId());
                buttonVO.setButtonCode(deyiButton.getButtonCode());
                buttonVO.setButtonName(deyiButton.getButtonName());
                buttonVO.setMenuId(deyiButton.getMenuId());
                buttonVO.setMenuCode(menuMapper.selectById(deyiButton.getMenuId()).getMenuCode());
                buttonVOS.add(buttonVO);
            }
        }

        return buttonVOS;
    }

    @Override
    public List<DeyiButton> getButtonByMenuId(Long menuId) {
        return buttonMapper.selectList(new LambdaQueryWrapper<DeyiButton>()
                .eq(DeyiButton::getIsDeleted, false)
                .eq(DeyiButton::getMenuId, menuId)
        );
    }

    @Override
    public String updateButton(DeyiButton deyiButton) {
        List<DeyiButton> deyiButtons = buttonMapper.selectList(new LambdaQueryWrapper<DeyiButton>()
                .eq(DeyiButton::getIsDeleted, false)
                .eq(DeyiButton::getButtonCode, deyiButton.getButtonCode())
                .eq(DeyiButton::getMenuId, deyiButton.getMenuId())
                .ne(DeyiButton::getId, deyiButton.getId())
        );
        if (deyiButtons.size() > 0)
            throw new BaseKnowException(10000, "该菜单下已存在当前编号的按钮");

        buttonMapper.update(new DeyiButton(), new LambdaUpdateWrapper<DeyiButton>()
                .set(DeyiButton::getButtonCode, deyiButton.getButtonCode())
                .set(DeyiButton::getButtonName, deyiButton.getButtonName())
                .eq(DeyiButton::getId, deyiButton.getId())
        );
        return "Success";
    }

    @Override
    public List<MenuButtonTreeVO> getMenuButtonTree() {
        List<MenuButtonTreeVO> menuButtonTreeVOList = new ArrayList<>();

        // 先获取有按钮的菜单
        List<DeyiButton> deyiButtons = buttonMapper.selectList(new LambdaQueryWrapper<DeyiButton>()
                .select(DeyiButton::getMenuId)
                .eq(DeyiButton::getIsDeleted, false)
                .groupBy(DeyiButton::getMenuId));
        // 循环菜单获取对应的按钮
        for (DeyiButton deyiButton : deyiButtons) {
            MenuButtonTreeVO menuButtonTreeVO = new MenuButtonTreeVO();
            DeyiMenu deyiMenu = menuMapper.selectOne(new LambdaQueryWrapper<DeyiMenu>()
                    .eq(DeyiMenu::getIsDeleted, false)
                    .eq(DeyiMenu::getId, deyiButton.getMenuId()));
            if (deyiMenu == null)// 菜单已删除则跳过
                continue;

            menuButtonTreeVO.setCode(deyiMenu.getMenuCode());
            menuButtonTreeVO.setName(deyiMenu.getMenuName());
            menuButtonTreeVO.setId(MessageFormat.format("菜单id:{0}",deyiMenu.getId()));

            List<DeyiButton> buttonData = buttonMapper.selectList(new LambdaQueryWrapper<DeyiButton>()
                    .eq(DeyiButton::getIsDeleted, false)
                    .eq(DeyiButton::getMenuId, deyiButton.getMenuId()));
            // 放入按钮数据
            for (DeyiButton buttonDatum : buttonData) {
                MenuButtonTreeVO.ButtonTreeVO buttonTreeVO = new MenuButtonTreeVO.ButtonTreeVO();
                buttonTreeVO.setId(buttonDatum.getId());
                buttonTreeVO.setCode(buttonDatum.getButtonCode());
                buttonTreeVO.setName(buttonDatum.getButtonName());
                menuButtonTreeVO.getChildren().add(buttonTreeVO);
            }
            menuButtonTreeVOList.add(menuButtonTreeVO);
        }
        return menuButtonTreeVOList;
    }
}
