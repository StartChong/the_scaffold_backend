package com.biology.commons.interact.bo.sys.user;

import com.biology.commons.constant.Constants;
import com.biology.commons.xss.Xss;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-27 15:39
 * @desc:
 */
@Data
public class SysUserBo implements Serializable {

    private static final long serialVersionUID = -1766529190019882820L;

    @Xss(message = "账号不能包含脚本字符")
    @Size(max = 20, message = "账号长度不能超过20个字符")
    @NotBlank(message = "账号不能为空！")
    @ApiModelProperty(value = "账号", required = true)
    private String account;

    @ApiModelProperty(value = "密码(密码以字母开头，长度在6~18之间，只能包含字符、数字和下划线)", required = true)
    @Pattern(regexp = Constants.REG_PASS_WORD, message = "密码以字母开头，长度在6~18之间，只能包含字符、数字和下划线！")
    @NotBlank(message = "密码不能为空！")
    private String password;

    @Xss(message = "用户名不能包含脚本字符")
    @Size(max = 30, message = "用户名长度不能超过30个字符")
    @ApiModelProperty(value = "用户名")
    private String userName;

    @Size(max = 11, message = "手机号码长度不能超过11个字符")
    @Pattern(regexp = Constants.REG_PHONE, message = "请输入正确格式的手机号码！")
    @NotBlank(message = "手机号不能为空！")
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @NotNull(message = "性别(0:男；1:女)不能为空")
    @ApiModelProperty(value = "性别(0:男；1:女)", required = true)
    private Integer sex;

    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    @ApiModelProperty(value = "邮箱", required = true)
    @NotBlank(message = "邮箱不能为空!")
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "角色编号集")
    private List<String> roleIds;

}
