package com.fengxi.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * 菜单实体类
 *
 * @author wujiuhe
 * @description: TODO
 * @title: DeyiMenu
 * @projectName FengXiDemo
 * @date 2023/1/28 14:09:09
 */
@Data
@Entity
@TableName("deyi_menu")
@ApiModel(description = "菜单数据")
public class DeyiMenu extends BaseEntity implements Serializable {

    @TableField("menu_code")
    @ApiModelProperty(value = "菜单编号")
    private String menuCode;

    @TableField("menu_name")
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @TableField("menu_url")
    @ApiModelProperty(value = "菜单url")
    private String menuUrl;

    @TableField("level")
    @ApiModelProperty(value = "菜单等级,最低为1")
    private Integer level;

    @TableField("icon")
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @TableField("parent_id")
    @ApiModelProperty(value = "父菜单id")
    private Long parentId;

    @TableField("number")
    @ApiModelProperty(value = "序号")
    private Integer number;
}
