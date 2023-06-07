package com.biology.service.common;

import com.biology.commons.interact.vo.minio.LeveResultVo;
import com.biology.commons.interact.vo.minio.MinioOpenSlideVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-10 12:54
 * @desc:
 */
public interface ImageService {

    /**
     * uploadToMinioByOpenSlide
     * 上传文件到minio服务器上并通过openSlide去切割
     *
     * @param file
     * @author: lichong
     * @description
     * @date 2022/5/10
     */
    MinioOpenSlideVo uploadToMinioByOpenSlide(MultipartFile file);

    /**
     * download
     * 下载minio中文件名对应文件
     *
     * @param minFileName
     * @return response
     * @author: lichong
     * @description
     * @date 2022/5/10
     */
    void download(String minFileName, HttpServletResponse response);

    /**
     * getOpenSlideLevel
     * 获取切割存储图
     *
     * @param minioOpenSlideVo
     * @author: lichong
     * @description
     * @date 2022/5/11
     */
    List<LeveResultVo> getOpenSlideLevel(MinioOpenSlideVo minioOpenSlideVo);

}
