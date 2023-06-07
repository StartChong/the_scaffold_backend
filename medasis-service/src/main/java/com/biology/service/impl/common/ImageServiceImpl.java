package com.biology.service.impl.common;

import com.biology.commons.constant.Constants;
import com.biology.commons.enums.sys.ResultCodeEnum;
import com.biology.commons.exception.SysException;
import com.biology.commons.interact.vo.minio.LeveResultVo;
import com.biology.commons.interact.vo.minio.MinIoUploadVo;
import com.biology.commons.interact.vo.minio.MinioOpenSlideVo;
import com.biology.commons.interact.vo.minio.OpenSlideLevelVo;
import com.biology.commons.utils.*;
import com.biology.service.common.ImageService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-10 12:54
 * @desc:
 */
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {


    @Autowired
    private MinIoUtils minIoUtils;

    @Autowired
    private OpenSlideUtil openSlideUtil;

    @Autowired
    private RedisUtil redisUtil;

    // 存储桶名称
    @Value("${min.io.bucketName}")
    private String bucketName;

    // 过期时间
    @Value("${min.io.expiry}")
    private Integer expiry;

    @Override
    public MinioOpenSlideVo uploadToMinioByOpenSlide(MultipartFile file) {
        MinioOpenSlideVo minioOpenSlideVo = new MinioOpenSlideVo();
        SysException.throwException(StringUtils.isBlank(file.getOriginalFilename()), ResultCodeEnum.PARAMETER_EXCEPTION.code(), "上传文件名异常！");
        // 文件路径前缀
        String preFix = DateUtils.datePath();
        String directory = preFix + Constants.SLANT + file.getOriginalFilename().split("\\.")[Constants.ZERO];
        // 上传源文件
        MinIoUploadVo minIoUploadVo = minIoUtils.upload(file, bucketName, directory);
        // 切割文件上传
        MinioOpenSlideVo openSlideVo = openSlideUtil.cutFileToMinIo(file, bucketName, directory);

        BeanUtils.copyProperties(openSlideVo, minioOpenSlideVo);
        minioOpenSlideVo.setBucketName(bucketName);
        minioOpenSlideVo.setInPutPath(minIoUploadVo.getMinFileName());
        minioOpenSlideVo.setFilePathPrefix(directory);
        return minioOpenSlideVo;
    }

    @Override
    public void download(String minFileName, HttpServletResponse response) {
        minIoUtils.download(response, bucketName, minFileName);
    }

    @Override
    public List<LeveResultVo> getOpenSlideLevel(MinioOpenSlideVo minioOpenSlideVo) {
        List<LeveResultVo> leveResultVos = Lists.newArrayList();
        if (Objects.nonNull(minioOpenSlideVo)) {
            leveResultVos = redisUtil.getCacheObject(Constants.LEVEL_RESULT
                    + minioOpenSlideVo.getBucketName()
                    + Constants.UNDERSCORE
                    + minioOpenSlideVo.getFilePathPrefix());
            if (!CollectionUtils.isEmpty(leveResultVos)) {
                return leveResultVos;
            }
            leveResultVos = Lists.newArrayList();
            Map<Integer, List<OpenSlideLevelVo>> resultMap = new HashMap<>();
            List<String> fileNames = minIoUtils.listObjectNames(minioOpenSlideVo.getBucketName(), minioOpenSlideVo.getFilePathPrefix());
            for (String fileName : fileNames) {
                String[] names = fileName.split(Constants.SLANT);
                if (fileName.contains(Constants.UNDERSCORE) && names.length >= 7) {
                    OpenSlideLevelVo openSlideLevelVo = new OpenSlideLevelVo();
                    String url = minIoUtils.getObjectUrl(minioOpenSlideVo.getBucketName(), fileName, expiry);
                    openSlideLevelVo.setLeveCount(Integer.valueOf(names[names.length - 3]));
                    String coordinateName = names[names.length - 2];
                    String[] coordinateArr = coordinateName.split(Constants.UNDERSCORE);
                    if (coordinateArr.length == 3) {
                        openSlideLevelVo.setX(Integer.valueOf(coordinateArr[1]));
                        openSlideLevelVo.setY(Integer.valueOf(coordinateArr[2]));
                    }
                    openSlideLevelVo.setTemporaryURL(url);
                    List<OpenSlideLevelVo> levelVos = resultMap.get(openSlideLevelVo.getLeveCount());
                    if (CollectionUtils.isEmpty(levelVos)) {
                        levelVos = Lists.newArrayList();
                    }
                    levelVos.add(openSlideLevelVo);
                    resultMap.put(openSlideLevelVo.getLeveCount(), levelVos);
                }
            }
            // 层级内排序
            for (Integer key : resultMap.keySet()) {
                LeveResultVo leveResultVo = new LeveResultVo();
                leveResultVo.setLeveCount(key);
                leveResultVo.setOpenSlideLevelVos(resultMap.get(key).stream()
                        .sorted(Comparator.comparing(OpenSlideLevelVo::getX)
                                .thenComparing(OpenSlideLevelVo::getY))
                        .collect(Collectors.toList()));
                leveResultVos.add(leveResultVo);
            }
            // 层级排序
            leveResultVos = leveResultVos.stream()
                    .sorted(Comparator.comparing(LeveResultVo::getLeveCount))
                    .collect(Collectors.toList());
            // 加入缓存
            redisUtil.setCacheObject(Constants.LEVEL_RESULT
                    + minioOpenSlideVo.getBucketName()
                    + Constants.UNDERSCORE
                    + minioOpenSlideVo.getFilePathPrefix(), leveResultVos, expiry - 1, TimeUnit.MINUTES);
        }
        return leveResultVos;
    }
}
