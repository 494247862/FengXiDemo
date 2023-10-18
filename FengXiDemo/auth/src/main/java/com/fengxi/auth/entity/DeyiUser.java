package com.fengxi.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 用户实体类
 *
 * @author wujiuhe
 * @description: TODO
 * @title: DeyiUser
 * @projectName demo
 * @date 2023/1/8 15:32:38
 */
@Data
@Entity
@TableName("deyi_user")
@ApiModel(description = "用户数据")
public class DeyiUser extends BaseEntity implements Serializable {

    @TableField("account")
    @ApiModelProperty(value = "用户名/账号")
    private String account;

    @TableField("nick_name")
    @ApiModelProperty(value = "名称")
    private String nickName;

    @TableField("password")
    @ApiModelProperty(value = "密码")
    private String password;

    @TableField("sex")
    @ApiModelProperty(value = "性别")
    private String sex;

    @TableField("phone")
    @ApiModelProperty(value = "电话号码")
    private String phone;

    @TableField("role_ids")
    @ApiModelProperty(value = "角色id")
    private String roleIds;

    @TableField("department_ids")
    @ApiModelProperty(value = "部门id")
    private String departmentIds;

    @TableField("head_picture_id")
    @ApiModelProperty(value = "头像图片id")
    private Long headPictureId;

    //---非数据库字段
    @Transient
    @TableField(exist = false)
    private String token;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "角色名称")
    private List<String> roleList;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "旧密码")
    private String oldPassword;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "新密码")
    private String newPassword;
}
