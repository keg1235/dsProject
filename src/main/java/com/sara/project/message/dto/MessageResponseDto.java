package com.sara.project.message.dto;

import com.sara.project.dcp.Dcp;
import com.sara.project.dcp.dto.DcpResponseDto;
import com.sara.project.message.Message;
import com.sara.project.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDto {

    private Long id;

    private Integer ddcId;

    private String message;

    private Integer di;

    private LocalDateTime createDate;

    private String phone;

    private String gateway;

    public static MessageResponseDto of(
            Message message
    ) {

        return MessageResponseDto.builder()
                .id(message.getId())
                .gateway(message.getDdc().getGateway().getIpAddress())
                .ddcId(message.getDdc().getDdc())
                .di(message.getDdc().getDdc())
                .message(message.getMessage())
                .phone(message.getUser().getPoneNum())
                .createDate(message.getDateSystem())
                .build();
    }
}
