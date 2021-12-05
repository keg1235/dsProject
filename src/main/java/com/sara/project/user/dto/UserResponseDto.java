package com.sara.project.user.dto;

import com.sara.project.ddc.Ddc;
import com.sara.project.ddc.dto.DdcResponseDto;
import com.sara.project.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private String poneNum;
    private String adminYn;
    private String idUseYn;

    public static UserResponseDto of(
            User user
    ) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .poneNum(user.getPoneNum())
                .adminYn(user.getAdminYn())
                .idUseYn(user.getIdUseYn())
                .build();
    }
}
