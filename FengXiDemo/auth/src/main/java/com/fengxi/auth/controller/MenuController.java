package com.fengxi.auth.controller;

import com.fengxi.auth.dto.MenuQueryDTO;
import com.fengxi.auth.entity.DeyiMenu;
import com.fengxi.auth.service.MenuService;
import com.fengxi.auth.vo.MenuTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单控制器
 *
 * @author wujiuhe
 * @description: TODO
 * @title: MenuController
 * @projectName FengXiDemo
 * @date 2023/1/28 14:35:35
 */
@RestController
@Api(tags = "菜单控制器")
@RequestMapping("/menu")
public class MenuController {

    @Resource
    MenuService menuService;

    @PostMapping("/addMenu")
    @ApiOperation(value = "新增菜单")
    public String addMenu(@RequestBody DeyiMenu deyiMenu) {
        return menuService.addMenu(deyiMenu);
    }

    @GetMapping("/getMenuTrees")
    @ApiOperation(value = "获取菜单树")
    public List<MenuTreeVO> getMenuTrees() {
        return menuService.getMenuTrees();
    }

    @GetMapping("/getMenuTreesByRoleId/{roleId}")
    @ApiOperation(value = "通过角色id获取菜单树")
    public List<MenuTreeVO> getMenuTreesByRoleId(@PathVariable Long roleId) {
        return menuService.getMenuTreesByRoleId(roleId);
    }

    @GetMapping("/getMenuTreesByCurrent")
    @ApiOperation(value = "获取当前用户的权限菜单树")
    public List<MenuTreeVO> getMenuTreesByCurrent() {
        return menuService.getMenuTreesByCurrent();
    }

    @GetMapping("/getMenuByCurrent")
    @ApiModelProperty(value = "获取当前用户的菜单")
    public List<DeyiMenu> getMenuByCurrent() {
        return menuService.getMenuByCurrent();
    }

    @DeleteMapping("/deleteMenu/{id}")
    @ApiOperation(value = "删除菜单")
    public String deleteMenu(@ApiParam("菜单id") @PathVariable Long id) {
        return menuService.deleteMenu(id);
    }

    @PostMapping("/QueryMenuData")
    @ApiOperation(value = "查询菜单数据")
    public List<MenuTreeVO> QueryMenuData(@RequestBody MenuQueryDTO menuQueryDTO) {
        return menuService.QueryMenuData(menuQueryDTO);
    }

    @PostMapping("/updateMenu")
    @ApiOperation(value = "修改菜单")
    public String updateMenu(@RequestBody DeyiMenu deyiMenu) {
        return menuService.updateMenu(deyiMenu);
    }
}
