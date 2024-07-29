package cn.coisini.admin.controller.v1;


import cn.coisini.common.fastdfs.FastDFSClient;
import cn.coisini.model.common.dto.Result;
import cn.coisini.model.common.enums.ResultEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: xiaoxiang
 * @Description: 文件上传
 */
@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    private final FastDFSClient fastDFSClient;

    @Value("${fdfs.url}")
    private String fileServerUrl;

    public FileController(FastDFSClient fastDFSClient) {
        this.fastDFSClient = fastDFSClient;
    }

    @PostMapping("/upload")
    public Result<String> uploadFile(MultipartFile file) {/*形参 file*/
        try {
            //判断文件是否存在
            if (file == null) {
                throw new IllegalArgumentException("文件不存在");
            }
            String fileId = fastDFSClient.uploadFile(file);
            // http://192.168.20.128/group1/M00/00/00/wKgUgGR8RAWAAWexACAAGaFenCQ384.jpg
            return Result.ok(fileServerUrl + fileId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.error(ResultEnum.FAIL,"文件上传失败");
    }
    @GetMapping("/remove")
    public Result<String> delFile(String fileId) {
        // group1/M00/00/00/wKgUgGR8RAWAAWexACAAGaFenCQ384.jpg
        try {
            fastDFSClient.delFile(fileId);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error(ResultEnum.FAIL, "删除失败");
    }

    @PostMapping("/download")
    // 接口未实现
    public Result<String> downLoadFile(String groupName, String path) {
        if (groupName == null && path == null){
            return Result.error(ResultEnum.   DATA_NOT_EXIST);
        }
        try {
            return Result.ok(fastDFSClient.download(groupName, path));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
