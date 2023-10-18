package com.fengxi.auth.service.impl;

import com.fengxi.auth.dao.FileMapper;
import com.fengxi.auth.entity.DeyiFile;
import com.fengxi.auth.entity.DeyiUser;
import com.fengxi.auth.exception.BaseKnowException;
import com.fengxi.auth.service.FileService;
import com.fengxi.auth.service.LoginService;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author wujiuhe
 * @description: TODO
 * @title: FileServiceImpl
 * @projectName FengXiDemo
 * @date 2023/1/31 16:05:37
 */
@Service
@Log4j2
public class FileServiceImpl implements FileService {

    @Value("${common.file.path}")
    String filePath;

    @Resource
    FileMapper fileMapper;

    @Resource
    LoginService loginService;

    @Override
    public DeyiFile uploadFile(MultipartFile multipartFile) {
        // 图片后缀名
        List<String> pictureName = Arrays.asList("bmp", "jpg", "dib", "gif", "jfif", "jpeg", "png", "tif", "tiff", "ico");

        DeyiUser currentUser = null;
        try {
            currentUser = loginService.getCurrentUser();
        } catch (Exception e) {
        }

        // 文件名
        String fileName = String.valueOf(System.currentTimeMillis());
        String[] split = Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.");
        File file = new File(filePath + File.separator + fileName + "." + split[1]);
        FileOutputStream fileOutputStream = null;
        // 没有token传入文件，先判断是否为图片，不是图片不放行
        if (currentUser == null) {
            if (!pictureName.contains(split[1])) {
                throw new BaseKnowException(10000, "没有登录不可传入图片以外的文件");
            }
        }
        try {
            //判断文件父目录是否存在
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            fileOutputStream = new FileOutputStream(file);
            IOUtils.copy(multipartFile.getInputStream(), fileOutputStream);

            DeyiFile deyiFile = new DeyiFile();
            deyiFile.setFileName(fileName + "." + split[1]);
            deyiFile.setFileOldName(multipartFile.getOriginalFilename());
            deyiFile.setFileType(split[1]);
            deyiFile.setFilePath(filePath + File.separator + fileName + "." + split[1]);
            fileMapper.insert(deyiFile);
            return deyiFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.info("文件关闭错误", e);
            }
        }
        return null;
    }

    @Override
    public String deleteFile(Long id) {

        DeyiFile deyiFile = fileMapper.selectById(id);

        if (deyiFile == null)
            throw new BaseKnowException(10000, "该文件已删除");


        // 删除本地文件
        File file = new File(filePath + File.separator + deyiFile.getFileName());
        if (file.exists()) {
            file.delete();
        }
        // 删除数据库数据
        fileMapper.deleteById(id);
        return "Success";
    }
}
