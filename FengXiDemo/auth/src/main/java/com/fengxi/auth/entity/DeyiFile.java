package com.fengxi.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;

/**
 * 文件实体类
 *
 * @author wujiuhe
 * @description: TODO
 * @title: DeyiFile
 * @projectName FengXiDemo
 * @date 2023/2/1 10:41:45
 */
@Data
@ApiModel("文件实体类")
@Entity
@TableName("deyi_file")
public class DeyiFile extends BaseEntity {

    @TableField("file_name")
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @TableField("file_old_name")
    @ApiModelProperty(value = "文件上传前的名字")
    private String fileOldName;

    @TableField("file_type")
    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @TableField("file_path")
    @ApiModelProperty(value = "文件地址")
    private String filePath;

}
