package com.sara.project.user.dto;

import com.sara.project.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String passwordYn;
    private String poneNum;
    private String adminYn;
    private String idUseYn;

    public User of(
            Long id
    ) {

        return User.builder()
                .id(id)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .poneNum(this.poneNum)
                .adminYn(this.adminYn)
                .idUseYn(this.idUseYn)
                .build();
    }

    public User of(

    ) {
        return User.builder()
                .id(id)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .poneNum(this.poneNum)
                .adminYn(this.adminYn)
                .idUseYn(this.idUseYn)
                .build();
    }
}
