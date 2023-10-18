package com.fengxi.auth.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色DTO
 * @author wujiuhe
 * @description: TODO
 * @title: RoleDTO
 * @projectName FengXiDemo
 * @date 2023/1/28 17:24:14
 */
@Data
@ApiModel(value = "角色DTO")
public class RoleDTO {

    @ApiModelProperty(value = "角色编号")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "菜单id")
    private List<Long> menuIds;

    @ApiModelProperty(value = "按钮id")
    private List<Long> buttonIds;
}
