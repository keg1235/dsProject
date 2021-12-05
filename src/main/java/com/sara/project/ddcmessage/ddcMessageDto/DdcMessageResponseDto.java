package com.sara.project.ddcmessage.ddcMessageDto;

import com.sara.project.ddc.Ddc;
import com.sara.project.ddc.dto.DdcResponseDto;
import com.sara.project.ddcmessage.DdcMessage;
import com.sara.project.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DdcMessageResponseDto {

    private Long id;
    private Integer di;
    private String name;
    private String use;
    private String color;
    private Integer ddc;
    private Integer seq;
    private String location;
    private Long gatewayId;
    private String gateway;
    private String message;
    private String email;
    private String phone;

    public DdcMessageResponseDto(Long id, Integer ddc, String name, String email, String phone,Integer di) {
        this.id = id;
        this.ddc = ddc;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.di = di;

    }


    public static DdcMessageResponseDto of(
            DdcUser ddcUser
    ) {
        return DdcMessageResponseDto.builder()
                .id(ddcUser.getId())
                .di(ddcUser.getDi())
                .name(ddcUser.getName())
                .ddc(ddcUser.getDdc())
                .email(ddcUser.getEmail())
                .phone(ddcUser.getPhone())
                .build();
    }
/*
    public static DdcMessageResponseDto of(

            DdcMessage ddcMessage
    ) {
        return DdcMessageResponseDto.builder()
                .id(ddcMessage.getId())
                .di(ddcMessage.getDdc().getDi())
                .use(ddcMessage.getDdc().getUse())
                .message(ddcMessage.getDdc().getMessage())
                .gateway(ddcMessage.getDdc().getGateway().getIpAddress())
                .name(ddcMessage.getUser().getName())
                .location(ddcMessage.getDdc().getLocation())
                .ddc(ddcMessage.getDdc().getDdc())
                .build();
    }

 */
}
