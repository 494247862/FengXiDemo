package com.fengxi.auth.controller;

import com.fengxi.auth.dao.FileMapper;
import com.fengxi.auth.entity.DeyiFile;
import com.fengxi.auth.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 文件控制器
 *
 * @author wujiuhe
 * @description: TODO
 * @title: FileController
 * @projectName FengXiDemo
 * @date 2023/1/31 16:14:23
 */
@Api(tags = "文件控制器")
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    FileService fileService;

    @Resource
    FileMapper fileMapper;

    @Value("${common.file.path}")
    String filePath;

    @PostMapping("/uploadFile")
    @ApiOperation(value = "上传文件")
    public DeyiFile uploadFile(@ApiParam(value = "上传文件") MultipartFile file) {
        return fileService.uploadFile(file);
    }

    @DeleteMapping("/deleteFile/{id}")
    @ApiOperation(value = "删除文件")
    public String deleteFile(@ApiParam(value = "文件id") @PathVariable Long id) {
        return fileService.deleteFile(id);
    }

    /**
     * 前端通过url路径即可访问图片
     *
     * @throws IOException
     */
    @GetMapping("/getImage/{id}")
    @ApiOperation("前端通过url路径即可访问图片")
    public void getImage(HttpServletResponse response, @ApiParam(value = "文件id") @PathVariable Long id) throws IOException {
        DeyiFile deyiFile = fileMapper.selectById(id);

        InputStream fis = null;
        OutputStream os = null;
        String fileName = URLEncoder.encode(deyiFile.getFileOldName(), "UTF-8");
        //设置响应头中文件的下载方式为附件方式，以及设置文件名
        response.setHeader("Content-Disposition","attachment; filename=" + fileName);
        //设置响应头的编码格式为UTF-8
        response.setCharacterEncoding("UTF-8");
        try {
            fis = new FileInputStream(filePath + File.separator + deyiFile.getFileName());//服务器本地图片地址
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
