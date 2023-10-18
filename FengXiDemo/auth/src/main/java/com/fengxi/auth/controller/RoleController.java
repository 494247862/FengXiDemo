package com.fengxi.auth.controller;

import com.fengxi.auth.dto.RoleDTO;
import com.fengxi.auth.dto.RolePageDTO;
import com.fengxi.auth.entity.DeyiRole;
import com.fengxi.auth.entity.DeyiUser;
import com.fengxi.auth.service.RoleService;
import com.fengxi.auth.vo.ResultPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色控制器
 *
 * @author wujiuhe
 * @description: TODO
 * @title: RoleController
 * @projectName FengXiDemo
 * @date 2023/1/28 17:13:18
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色控制器")
public class RoleController {

    @Resource
    RoleService roleService;

    @PostMapping("/addRole")
    @ApiOperation(value = "新增角色")
    public String addRole(@RequestBody RoleDTO roleDTO) {
        return roleService.addRole(roleDTO);
    }

    @PostMapping("/updateRole")
    @ApiOperation(value = "编辑角色")
    public String updateRole(@RequestBody RoleDTO roleDTO) {
        return roleService.updateRole(roleDTO);
    }

    @GetMapping("/getAllRoles")
    @ApiOperation(value = "获取所有角色")
    public List<DeyiRole> getAllRoles() {
        return roleService.getAllRoles();
    }

    @DeleteMapping("/deleteRole/{id}")
    @ApiOperation(value = "删除角色")
    public String deleteRole(@ApiParam("角色id") @PathVariable Long id) {
        return roleService.deleteRole(id);
    }

    @PostMapping("/QueryRolePage")
    @ApiOperation("分页获取角色数据")
    public ResultPageVO<DeyiRole> QueryRolePage(@RequestBody RolePageDTO rolePageDTO) {
        return roleService.QueryRolePage(rolePageDTO);
    }

    @GetMapping("/getUserByCode/{code}")
    @ApiOperation(value = "通过角色编号获取用户")
    public List<DeyiUser> getUserByCode(@PathVariable String code) {
        return roleService.getUserByCode(code);
    }
}
