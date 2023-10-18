package com.fengxi.auth.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 按钮VO
 *
 * @author wujiuhe
 * @description: TODO
 * @title: ButtonVO
 * @projectName FengXiDemo
 * @date 2023/1/30 17:46:04
 */
@Data
@ApiModel("按钮VO")
public class ButtonVO {

    private Long id;

    @ApiModelProperty(value = "按钮编号")
    private String buttonCode;

    @ApiModelProperty(value = "按钮名称")
    private String buttonName;

    @ApiModelProperty(value = "按钮所属菜单Id")
    private Long menuId;

    @ApiModelProperty(value = "按钮所属菜单code")
    private String menuCode;

}
