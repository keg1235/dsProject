package com.sara.project.ddc.dto;

import com.sara.project.ddc.Ddc;
import com.sara.project.gateway.dto.GatewayResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DdcResponseDto {

    private Long id;
    private Integer di;
    private String name;
    private String use;
    private String color;
    private Integer ddc;
    private Integer seq;
    private String location;
    private Long gatewayId;
    private String gatewayIp;
    private String message;
    private Integer status;
    private Integer doStatus;
    private Integer doId;
    private Integer dcu;
    private String diUse;
    private String doUse;
    private boolean click;

    public static DdcResponseDto of(
            Ddc ddc
    ) {
        /*
        String ipAddress=null;
        if (ddc.getGateway().getIpAddress() != null){
            ipAddress = ddc.getGateway().getIpAddress();
        }*/
        return DdcResponseDto.builder()
                .id(ddc.getId())
                .di(ddc.getDi())
                .use(ddc.getUse())
                .message(ddc.getMessage())
//                .gatewayIp(ddc.getGateway().getIpAddress())
                .gatewayId(ddc.getGateway().getId())
                .name(ddc.getName())
                .location(ddc.getLocation())
                .ddc(ddc.getDdc())
                .seq(ddc.getSeq())
                .color(ddc.getColor())
                .status(ddc.getDiStatus())
                .doId(ddc.getDoId())
                .doStatus(ddc.getDoStatus())
                .dcu(ddc.getDcu())
                .diUse(ddc.getDiUse())
                .doUse(ddc.getDoUse())
                .click(false)
                .build();
    }
}
