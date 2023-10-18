package com.fengxi.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fengxi.auth.dao.ButtonMapper;
import com.fengxi.auth.dao.MenuMapper;
import com.fengxi.auth.dao.RoleMapper;
import com.fengxi.auth.dao.UserMapper;
import com.fengxi.auth.dto.CommonSettingDTO;
import com.fengxi.auth.dto.MenuQueryDTO;
import com.fengxi.auth.entity.DeyiButton;
import com.fengxi.auth.entity.DeyiMenu;
import com.fengxi.auth.entity.DeyiRole;
import com.fengxi.auth.entity.DeyiUser;
import com.fengxi.auth.exception.BaseKnowException;
import com.fengxi.auth.service.MenuService;
import com.fengxi.auth.utils.JwtUtil;
import com.fengxi.auth.vo.MenuTreeVO;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wujiuhe
 * @description: TODO
 * @title: MenuServiceImpl
 * @projectName FengXiDemo
 * @date 2023/1/28 14:39:08
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    UserMapper userMapper;

    @Resource
    MenuMapper menuMapper;

    @Resource
    RoleMapper roleMapper;

    @Resource
    ButtonMapper buttonMapper;

    @Resource
    private HttpServletRequest request;

    @Resource
    private CommonSettingDTO commonSettingDTO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addMenu(DeyiMenu deyiMenu) {

        DeyiMenu menu = menuMapper.selectOne(new LambdaQueryWrapper<DeyiMenu>()
                .eq(DeyiMenu::getIsDeleted, false)
                .eq(DeyiMenu::getMenuCode, deyiMenu.getMenuCode())
        );

        if (menu != null) {
            throw new BaseKnowException(10000, "该菜单编号已存在");
        }
        menuMapper.insert(deyiMenu);

        return "Success";
    }

    @Override
    public List<MenuTreeVO> getMenuTrees() {
        // 获取所有菜单
        List<DeyiMenu> deyiMenus = menuMapper.selectList(new LambdaQueryWrapper<DeyiMenu>()
                .orderByAsc(DeyiMenu::getNumber)
                .eq(DeyiMenu::getIsDeleted, false));
        // 获取菜单的按钮数据
        for (DeyiMenu deyiMenu : deyiMenus) {
            List<DeyiButton> deyiButtons = buttonMapper.selectList(new LambdaQueryWrapper<DeyiButton>()
                    .eq(DeyiButton::getIsDeleted, false)
                    .eq(DeyiButton::getMenuId, deyiMenu.getId())
            );
        }
        return getMenuTreeVOS(deyiMenus);
    }

    @Override
    public List<MenuTreeVO> getMenuTreesByRoleId(Long roleId) {
        DeyiRole deyiRole = roleMapper.selectById(roleId);

        if (Objects.isNull(deyiRole.getMenuIds()))
            return new ArrayList<MenuTreeVO>();

        List<Long> menuIds = Arrays.stream(deyiRole.getMenuIds().split(",")).map(Long::parseLong).collect(Collectors.toList());

        if (menuIds.size() > 0) {
            List<DeyiMenu> deyiMenus = menuMapper.selectList(new LambdaQueryWrapper<DeyiMenu>()
                    .orderByAsc(DeyiMenu::getNumber)
                    .eq(DeyiMenu::getIsDeleted, false)
                    .in(DeyiMenu::getId, menuIds)
            );
            return getMenuTreeVOS(deyiMenus);
        } else {
            return new ArrayList<MenuTreeVO>();
        }
    }

    @Override
    public List<MenuTreeVO> getMenuTreesByCurrent() {
        String token = request.getHeader("token");
        Claims claims = JwtUtil.parseToken(token, commonSettingDTO.getTokenKey());
        String userId = claims.get("userId").toString();
        DeyiUser deyiUser = userMapper.selectById(userId);

        // 获取角色ids
        if (!Objects.isNull(deyiUser.getRoleIds())) {
            // 获取角色list
            List<Long> roleIds = Arrays.stream(deyiUser.getRoleIds().split(",")).map(Long::valueOf).collect(Collectors.toList());
            // 通过角色list获取菜单list
            List<Long> menuIds = new ArrayList<>();
            List<Long> buttonIds = new ArrayList<>();
            List<DeyiRole> deyiRoles = roleMapper.selectList(new LambdaQueryWrapper<DeyiRole>()
                    .eq(DeyiRole::getIsDeleted, false)
                    .in(DeyiRole::getId, roleIds));
            for (DeyiRole deyiRole : deyiRoles) {
                // 找菜单id
                if (!Objects.isNull(deyiRole.getMenuIds())) {
                    menuIds.addAll(Arrays.stream(deyiRole.getMenuIds().split(",")).map(Long::valueOf).collect(Collectors.toList()));
                }
                // 找按钮id
                if (!Objects.isNull(deyiRole.getButtonIds())) {
                    buttonIds.addAll(Arrays.stream(deyiRole.getButtonIds().split(",")).map(Long::valueOf).collect(Collectors.toList()));
                }
            }
            // 先查询权限内的菜单
            List<DeyiMenu> deyiMenus = menuMapper.selectList(new LambdaQueryWrapper<DeyiMenu>()
                    .eq(DeyiMenu::getIsDeleted, false)
                    .orderByAsc(DeyiMenu::getNumber)
                    .in(DeyiMenu::getId, menuIds));

            List<MenuTreeVO> menuTreeVOS = getMenuTreeVOS(deyiMenus);

            return menuTreeVOS;
        }


        return new ArrayList<>();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteMenu(Long id) {

        List<Long> ids = new ArrayList<>();
        getChildrenMenu(menuMapper.selectById(id), ids);
        menuMapper.update(new DeyiMenu(), new LambdaUpdateWrapper<DeyiMenu>()
                .set(DeyiMenu::getIsDeleted, true)
                .in(DeyiMenu::getId, ids)
        );
        return "Success";
    }

    @Override
    public List<MenuTreeVO> QueryMenuData(MenuQueryDTO menuQueryDTO) {
        // 没有查询数据就返回全部树
        if (StringUtils.isEmpty(menuQueryDTO.getMenuName()) && StringUtils.isEmpty(menuQueryDTO.getMenuCode()) && StringUtils.isEmpty(menuQueryDTO.getMenuUrl())) {
            return getMenuTrees();
        }

        // 获取符合条件的菜单
        List<DeyiMenu> deyiMenus = menuMapper.selectList(new LambdaQueryWrapper<DeyiMenu>()
                .orderByAsc(DeyiMenu::getNumber)
                .eq(DeyiMenu::getIsDeleted, false)
                .like(StringUtils.isNotEmpty(menuQueryDTO.getMenuName()), DeyiMenu::getMenuName, menuQueryDTO.getMenuName())
                .like(StringUtils.isNotEmpty(menuQueryDTO.getMenuCode()), DeyiMenu::getMenuCode, menuQueryDTO.getMenuCode())
                .like(StringUtils.isNotEmpty(menuQueryDTO.getMenuUrl()), DeyiMenu::getMenuUrl, menuQueryDTO.getMenuUrl()));
        // 然后获取其中自己和父节点的数据
        List<Long> ids = new ArrayList<>();
        for (DeyiMenu deyiMenu : deyiMenus) {
            if (deyiMenu.getLevel() == 1) {
                ids.add(deyiMenu.getId());
            } else {
                getParentMenu(deyiMenu, ids);
            }
        }
        if (ids.size() <= 0)
            return new ArrayList<>();
        List<DeyiMenu> deyiMenus1 = menuMapper.selectBatchIds(ids);

        return getMenuTreeVOS(deyiMenus1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateMenu(DeyiMenu deyiMenu) {
        menuMapper.update(new DeyiMenu(), new LambdaUpdateWrapper<DeyiMenu>()
                .set(DeyiMenu::getMenuName, deyiMenu.getMenuName())
                .set(DeyiMenu::getMenuCode, deyiMenu.getMenuCode())
                .set(DeyiMenu::getMenuUrl, deyiMenu.getMenuUrl())
                .set(DeyiMenu::getIcon, deyiMenu.getIcon())
                .set(DeyiMenu::getNumber, deyiMenu.getNumber())
                .eq(DeyiMenu::getId, deyiMenu.getId())

        );
        return "Success";
    }

    @Override
    public List<DeyiMenu> getMenuByCurrent() {
        String token = request.getHeader("token");
        Claims claims = JwtUtil.parseToken(token, commonSettingDTO.getTokenKey());
        String userId = claims.get("userId").toString();
        DeyiUser deyiUser = userMapper.selectById(userId);
        // 获取角色ids
        if (!Objects.isNull(deyiUser.getRoleIds())) {
            // 获取角色list
            List<Long> roleIds = Arrays.stream(deyiUser.getRoleIds().split(",")).map(Long::valueOf).collect(Collectors.toList());
            List<DeyiRole> deyiRoles = roleMapper.selectList(new LambdaQueryWrapper<DeyiRole>()
                    .eq(DeyiRole::getIsDeleted, false)
                    .in(DeyiRole::getId, roleIds));
            // 通过角色list获取菜单list
            List<Long> menuIds = new ArrayList<>();
            for (DeyiRole deyiRole : deyiRoles) {
                // 找菜单id
                if (!Objects.isNull(deyiRole.getMenuIds())) {
                    menuIds.addAll(Arrays.stream(deyiRole.getMenuIds().split(",")).map(Long::valueOf).collect(Collectors.toList()));
                }
            }
            // 查询权限内的菜单
            List<DeyiMenu> deyiMenus = menuMapper.selectList(new LambdaQueryWrapper<DeyiMenu>()
                    .eq(DeyiMenu::getIsDeleted, false)
                    .orderByAsc(DeyiMenu::getNumber)
                    .in(DeyiMenu::getId, menuIds));
            return deyiMenus;
        }

        return new ArrayList<>();
    }

    /**
     * 获取菜单树
     *
     * @param deyiMenus
     * @return
     */
    private List<MenuTreeVO> getMenuTreeVOS(List<DeyiMenu> deyiMenus) {
        List<MenuTreeVO> menuTreeVOS = new ArrayList<>();
        // 获取所有一级菜单
        List<DeyiMenu> oneMenus = deyiMenus.stream().filter(t -> t.getLevel().equals(1)).collect(Collectors.toList());

        for (DeyiMenu oneMenu : oneMenus) {
            MenuTreeVO menuTreeVO = buildChilTree(oneMenu, deyiMenus);
            menuTreeVOS.add(menuTreeVO);
        }
        return menuTreeVOS;
    }

    /**
     * 递归，建立子树形结构
     *
     * @param pNode
     * @param menuList
     * @return
     */
    private MenuTreeVO buildChilTree(DeyiMenu pNode, List<DeyiMenu> menuList) {
        List<MenuTreeVO> chilMenus = new ArrayList<>();
        MenuTreeVO menuTree = new MenuTreeVO();
        menuTree.setId(pNode.getId());
        menuTree.setLevel(pNode.getLevel());
        menuTree.setMenuCode(pNode.getMenuCode());
        menuTree.setMenuName(pNode.getMenuName());
        menuTree.setMenuUrl(pNode.getMenuUrl());
        menuTree.setParentId(pNode.getParentId());
        menuTree.setIcon(pNode.getIcon());
        menuTree.setNumber(pNode.getNumber());
        for (DeyiMenu menuNode : menuList) {
            if (pNode.getId().equals(menuNode.getParentId())) {
                chilMenus.add(buildChilTree(menuNode, menuList));
            }
        }
        menuTree.setChildren(chilMenus);
        return menuTree;
    }

    /**
     * 递归查找父节点
     */
    private void getParentMenu(DeyiMenu pNode, List<Long> ids) {
        // 如果时父节点，停止递归
        if (pNode.getLevel() == 1) {
            ids.add(pNode.getId());
        } else {
            ids.add(pNode.getId());
            DeyiMenu deyiMenu = menuMapper.selectOne(new LambdaQueryWrapper<DeyiMenu>()
                    .eq(DeyiMenu::getIsDeleted, false)
                    .eq(DeyiMenu::getId, pNode.getParentId())
            );
            if (deyiMenu == null) {
                return;
            } else {
                getParentMenu(deyiMenu, ids);
            }
        }
    }

    /**
     * 递归，获取子节点
     *
     * @param pNode
     * @param ids
     */
    private void getChildrenMenu(DeyiMenu pNode, List<Long> ids) {
        List<DeyiMenu> deyiMenus = menuMapper.selectList(new LambdaQueryWrapper<DeyiMenu>()
                .orderByAsc(DeyiMenu::getNumber)
                .eq(DeyiMenu::getIsDeleted, false)
                .eq(DeyiMenu::getParentId, pNode.getId()));
        if (deyiMenus.size() <= 0) { // 没有子节点了
            ids.add(pNode.getId());
        } else {
            ids.add(pNode.getId());
            for (DeyiMenu deyiMenu : deyiMenus) {
                getChildrenMenu(deyiMenu, ids);
            }
        }
    }
}
