package com.fengxi.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;

/**
 * 按钮实体类
 *
 * @author wujiuhe
 * @description: TODO
 * @title: DeyiButton
 * @projectName FengXiDemo
 * @date 2023/1/30 16:25:36
 */
@Data
@Entity
@ApiModel("按钮实体类")
@TableName("deyi_button")
public class DeyiButton extends BaseEntity {

    @TableField(value = "button_code")
    @ApiModelProperty(value = "按钮编号")
    private String buttonCode;

    @TableField(value = "button_name")
    @ApiModelProperty(value = "按钮名称")
    private String buttonName;

    @TableField(value = "menu_id")
    @ApiModelProperty(value = "按钮所属菜单Id")
    private Long menuId;
}
