package com.fengxi.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单按钮树结构VO
 *
 * @author wujiuhe
 * @description: TODO
 * @title: MenuButtonTreeVO
 * @projectName FengXiDemo
 * @date 2023/2/6 18:13:21
 */
@Data
@ApiModel("菜单按钮树结构VO")
public class MenuButtonTreeVO {

    @ApiModelProperty("菜单id")
    private String id;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单编号")
    private String code;

    @ApiModelProperty("按钮数据")
    private List<ButtonTreeVO> children = new ArrayList<>();

    @Data
    public static class ButtonTreeVO {

        @ApiModelProperty("按钮id")
        private Long id;

        @ApiModelProperty("按钮名称")
        private String name;

        @ApiModelProperty("按钮编号")
        private String code;
    }
}
