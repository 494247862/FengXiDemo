package com.fengxi.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;

/**
 * 部门实体类
 *
 * @author wujiuhe
 * @description: TODO
 * @title: DeyiDepartment
 * @projectName FengXiDemo
 * @date 2023/1/29 17:11:02
 */
@Data
@Entity
@TableName("deyi_department")
@ApiModel(description = "部门数据")
public class DeyiDepartment extends BaseEntity {

    @ApiModelProperty(value = "部门编号")
    @TableField("department_code")
    private String departmentCode;

    @ApiModelProperty(value = "部门名称")
    @TableField("department_name")
    private String departmentName;

    @ApiModelProperty(value = "部门,最低为1")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value = "父部门id")
    @TableField("parent_id")
    private Long parentId;
}
