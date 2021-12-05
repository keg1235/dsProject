package com.sara.project.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponseDto {


    private String email;
    private String name;
    private String poneNum;
    private String adminYn;
    private boolean loginFlg;

    public UserLoginResponseDto(UserResponseDto userResponseEmailDto) {
        this.name = userResponseEmailDto.getName();
        this.email = userResponseEmailDto.getEmail();
        this.poneNum = userResponseEmailDto.getPoneNum();
        this.adminYn = userResponseEmailDto.getAdminYn();
        this.loginFlg = true;

    }

    public UserLoginResponseDto(boolean loginFlg) {
        this.name = null;
        this.email = null;
        this.poneNum = null;
        this.adminYn = null;
        this.loginFlg = false;

    }

    public static UserLoginResponseDto of(
            boolean loginFlg
    ) {
        return UserLoginResponseDto.builder()
                .name(null)
                .email(null)
                .poneNum(null)
                .adminYn("N")
                .loginFlg(false)
                .build();
    }


    public static UserLoginResponseDto of(
            UserResponseDto user
    ) {
            return UserLoginResponseDto.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .poneNum(user.getPoneNum())
                    .adminYn(user.getAdminYn())
                    .loginFlg(true)
                    .build();
    }

}
