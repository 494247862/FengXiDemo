package com.fengxi.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 菜单查询DTO
 *
 * @author wujiuhe
 * @description: TODO
 * @title: MenuQueryDTO
 * @projectName FengXiDemo
 * @date 2023/2/3 14:06:42
 */
@Data
@ApiModel("菜单查询DTO")
public class MenuQueryDTO {

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("菜单编号")
    private String menuCode;

    @ApiModelProperty("菜单url")
    private String menuUrl;
}
