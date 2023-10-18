package com.fengxi.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * 角色实体类
 * @author wujiuhe
 * @description: TODO
 * @title: DeyiRole
 * @projectName FengXiDemo
 * @date 2023/1/28 17:05:44
 */
@Data
@Entity
@TableName("deyi_role")
@ApiModel(description = "菜单数据")
public class DeyiRole extends BaseEntity implements Serializable {

    @TableField("role_code")
    @ApiModelProperty(value = "角色编号")
    private String roleCode;

    @TableField("role_name")
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @TableField("menu_ids")
    @ApiModelProperty(value = "菜单id")
    private String menuIds;

    @TableField("button_ids")
    @ApiModelProperty(value = "按钮id")
    private String buttonIds;
}
