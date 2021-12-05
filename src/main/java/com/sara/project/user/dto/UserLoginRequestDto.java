package com.sara.project.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "비밀번호")
    private String userPwd;
}
