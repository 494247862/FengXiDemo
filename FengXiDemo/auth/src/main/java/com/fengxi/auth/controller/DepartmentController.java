package com.fengxi.auth.controller;

import com.fengxi.auth.entity.DeyiDepartment;
import com.fengxi.auth.service.DepartmentService;
import com.fengxi.auth.vo.DepartmentTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门控制器
 *
 * @author wujiuhe
 * @description: TODO
 * @title: DepartmentController
 * @projectName FengXiDemo
 * @date 2023/1/29 17:29:34
 */
@RestController
@RequestMapping("/department")
@Api(tags = "部门控制器")
public class DepartmentController {
    @Resource
    DepartmentService departmentService;

    @PostMapping("/addDepartment")
    @ApiOperation(value = "新增部门")
    public String addDepartment(@RequestBody DeyiDepartment deyiDepartment) {
        return departmentService.addDepartment(deyiDepartment);
    }

    @PostMapping("/updateDepartment")
    @ApiOperation(value = "编辑部门")
    public String updateDepartment(@RequestBody DeyiDepartment deyiDepartment) {
        return departmentService.updateDepartment(deyiDepartment);
    }

    @PostMapping("/getDepartmentTree")
    @ApiOperation(value = "获取部门树")
    public List<DepartmentTreeVO> getDepartmentTree() {
        return departmentService.getDepartmentTree();
    }

    @DeleteMapping("/deleteDepartment/{id}")
    @ApiOperation(value = "删除部门")
    public String deleteDepartment(@ApiParam(value = "部门Id") @PathVariable Long id) {
        return departmentService.deleteDepartment(id);
    }
}
