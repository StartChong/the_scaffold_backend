package com.biology.commons.interact.bo.sys;

import com.biology.commons.constant.Constants;
import com.biology.commons.xss.Xss;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-27 11:36
 * @desc:
 */
@Data
public class UserPwdBo implements Serializable {

    private static final long serialVersionUID = 8882735759874884923L;

    @Xss(message = "旧密码不能包含脚本字符")
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "旧密码不能为空！")
    protected String oldPwd;

    @Xss(message = "新密码不能包含脚本字符")
    @ApiModelProperty(value = "密码", required = true)
    @Pattern(regexp = Constants.REG_PASS_WORD, message = "新密码以字母开头，长度在6~18之间，只能包含字符、数字和下划线！")
    @NotBlank(message = "新密码不能为空！")
    protected String newPwd;
}
