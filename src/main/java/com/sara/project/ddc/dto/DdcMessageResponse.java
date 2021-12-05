package com.sara.project.ddc.dto;

import com.sara.project.ddc.Ddc;
import com.sara.project.ddcmessage.ddcMessageDto.DdcUser;
import com.sara.project.message.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DdcMessageResponse {

    private Long id;
    private Integer di;
    private String name;
    private String use;
    private String color;
    private Integer ddc;
    private String location;
    private Integer seq;
    private String gateway;
    private Integer status;
    private Integer doStatus;
    private Integer doId;
    private Integer dcu;
    private String diUse;
    private String doUse;



    public static DdcMessageResponse of(
            Ddc ddc
    ) {
        return DdcMessageResponse.builder()
                .id(ddc.getId())
                .di(ddc.getDi())
                .use(ddc.getUse())
                .gateway(ddc.getGateway().getIpAddress())
                .name(ddc.getName())
                .location(ddc.getLocation())
                .ddc(ddc.getDdc())
                .seq(ddc.getSeq())
                .status(ddc.getDiStatus())
                .color(ddc.getColor())
                .doId(ddc.getDoId())
                .dcu(ddc.getDcu())
                .doStatus(ddc.getDoStatus())
                .diUse(ddc.getDiUse())
                .doUse(ddc.getDoUse())
                .build();
    }

}
