package com.fengxi.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 部门树VO
 *
 * @author wujiuhe
 * @description: TODO
 * @title: DepartmentTreeVO
 * @projectName FengXiDemo
 * @date 2023/1/30 9:57:46
 */
@Data
@ApiModel("部门树")
public class DepartmentTreeVO {
    private Long id;

    @ApiModelProperty(value = "部门编号")
    private String departmentCode;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "部门,最低为1")
    private Integer level;

    @ApiModelProperty(value = "父部门id")
    private Long parentId;

    @ApiModelProperty(value = "子项")
    private List<DepartmentTreeVO> children;
}
