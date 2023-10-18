package com.fengxi.auth.controller;

import com.fengxi.auth.entity.DeyiButton;
import com.fengxi.auth.service.ButtonService;
import com.fengxi.auth.vo.ButtonVO;
import com.fengxi.auth.vo.MenuButtonTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 按钮控制器
 *
 * @author wujiuhe
 * @description: TODO
 * @title: ButtonController
 * @projectName FengXiDemo
 * @date 2023/1/30 16:45:32
 */
@RestController
@RequestMapping("/button")
@Api(tags = "按钮控制器")
public class ButtonController {

    @Resource
    ButtonService buttonService;

    @PostMapping("/addButton")
    @ApiOperation(value = "新增按钮")
    public String addButton(@RequestBody DeyiButton deyiButton) {
        return buttonService.addButton(deyiButton);
    }

    @DeleteMapping("/deleteButton/{id}")
    @ApiOperation(value = "删除按钮")
    public String deleteButton(@ApiParam(value = "按钮id") @PathVariable Long id) {
        return buttonService.deleteButton(id);
    }

    @GetMapping("/getButtonByCurrent")
    @ApiOperation(value = "获取当前用户的权限按钮")
    public List<ButtonVO> getButtonByCurrent() {
        return buttonService.getButtonByCurrent();
    }

    @GetMapping("/getButtonByMenuId/{menuId}")
    @ApiOperation(value = "通过菜单id获取按钮数据")
    public List<DeyiButton> getButtonByMenuId(@ApiParam(value = "菜单id") @PathVariable Long menuId) {
        return buttonService.getButtonByMenuId(menuId);
    }

    @PostMapping("/updateButton")
    @ApiOperation(value = "修改按钮")
    public String updateButton(@RequestBody DeyiButton deyiButton) {
        return buttonService.updateButton(deyiButton);
    }

    @GetMapping("/getMenuButtonTree")
    @ApiOperation(value = "获取菜单按钮结构")
    public List<MenuButtonTreeVO> getMenuButtonTree() {
        return buttonService.getMenuButtonTree();
    }
}
