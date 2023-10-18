package com.fengxi.auth.service;

import com.fengxi.auth.entity.DeyiFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wujiuhe
 * @description: TODO
 * @title: FileService
 * @projectName FengXiDemo
 * @date 2023/1/31 16:03:15
 */
public interface FileService {

    /**
     * 上传文件
     *
     * @param multipartFile
     * @return
     */
    public DeyiFile uploadFile(MultipartFile multipartFile);

    /**
     * 删除文件
     *
     * @param id
     * @return
     */
    String deleteFile(Long id);

}
