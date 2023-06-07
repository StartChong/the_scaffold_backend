package com.biology.api.controller.business.upload;

import com.biology.commons.base.Result;
import com.biology.commons.interact.vo.minio.MinioOpenSlideVo;
import com.biology.service.common.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-06 18:38
 * @desc:
 */
@RestController
@Api(tags = "Minio文件上传")
@RequestMapping("/api/business/minio")
public class MinIoController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "(Minio)单文件上传(切割)", notes = "(Minio)单文件上传(切割)")
    public Result<MinioOpenSlideVo> upload(@RequestParam(value = "file") MultipartFile file) {

        return Result.success(imageService.uploadToMinioByOpenSlide(file));
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiOperation(value = "(Minio)单文件下载", notes = "(Minio)单文件下载")
    public void download(@RequestParam(value = "minFileName") String minFileName, HttpServletResponse response) {

        imageService.download(minFileName, response);
    }

}
