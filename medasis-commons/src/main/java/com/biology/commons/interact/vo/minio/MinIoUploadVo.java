package com.biology.commons.interact.vo.minio;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-05-06 18:19
 * @desc:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("minio文件存储信息")
public class MinIoUploadVo implements Serializable {

    private static final long serialVersionUID = -163850219426739456L;

    @ApiModelProperty(value = "文件名")
    private String minFileName;

    @ApiModelProperty(value = "文件存放地址")
    private String minFileUrl;

}
