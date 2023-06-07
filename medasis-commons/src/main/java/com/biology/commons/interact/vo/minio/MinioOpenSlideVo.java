package com.biology.commons.interact.vo.minio;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-10 17:48
 * @desc:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("minio-openSlide文件存储信息")
public class MinioOpenSlideVo implements Serializable {

    private static final long serialVersionUID = -2505154673374855299L;

    @ApiModelProperty(value = "文件存放桶")
    private String bucketName;

    @ApiModelProperty(value = "源文件存放地址")
    private String inPutPath;

    @ApiModelProperty(value = "文件存放地址前缀")
    private String filePathPrefix;

    @ApiModelProperty(value = "切割层总数")
    private Integer levelCount;

    @ApiModelProperty(value = "附件标识")
    private Set<String> appendixes;

}
