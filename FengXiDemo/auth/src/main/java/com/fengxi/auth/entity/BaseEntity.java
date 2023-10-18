package com.fengxi.auth.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 基础类 所有实体类都应继承
 *
 * @author wujiuhe
 * @description: TODO
 * @title: BaseEntity
 * @projectName demo
 * @date 2023/1/16 16:00:46
 */
@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    @TableField("id")
    @TableId(type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id", hidden = true)
    private Long id;

    @TableField(value = "create_user_id", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人", hidden = true)
    private Long createUserId;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间", hidden = true)
    @Column(name = "create_time", columnDefinition = "datetime(0)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(value = "update_user_id", fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "修改人", hidden = true)
    private Long updateUserId;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "更新时间", hidden = true)
    @Column(name = "update_time", columnDefinition = "datetime(0)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否删除", hidden = true)
    private Boolean isDeleted;
}
