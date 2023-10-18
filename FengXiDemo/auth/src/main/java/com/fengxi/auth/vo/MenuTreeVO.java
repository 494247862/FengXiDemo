package com.fengxi.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树VO
 *
 * @author wujiuhe
 * @description: TODO
 * @title: MenuTreeVO
 * @projectName FengXiDemo
 * @date 2023/1/29 10:18:45
 */
@Data
@ApiModel(value = "菜单树VO")
public class MenuTreeVO {

    private Long id;

    @ApiModelProperty(value = "菜单编号")
    private String menuCode;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单url")
    private String menuUrl;

    @ApiModelProperty(value = "菜单等级,最低为1")
    private Integer level;

    @ApiModelProperty(value = "父菜单id")
    private Long parentId;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "序号")
    private Integer number;

    @ApiModelProperty(value = "子项")
    private List<MenuTreeVO> children = new ArrayList<>();
}
