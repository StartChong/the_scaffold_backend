package com.biology.commons.interact.bo.sys;

import com.biology.commons.xss.Xss;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-27 11:36
 * @desc:
 */
@Data
public class LoginBo implements Serializable {

    private static final long serialVersionUID = 864289518019187810L;

    @Xss(message = "用户名不能包含脚本字符")
    @ApiModelProperty(value = "账号", required = true)
    @NotBlank(message = "账号不能为空！")
    protected String account;

    @Xss(message = "密码不能包含脚本字符")
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空！")
    protected String password;
}
