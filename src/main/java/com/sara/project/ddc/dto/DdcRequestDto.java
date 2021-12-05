package com.sara.project.ddc.dto;

import com.sara.project.dcp.Dcp;
import com.sara.project.ddc.Ddc;
import com.sara.project.gateway.TbGateway;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DdcRequestDto {


    private Long id;
    private Integer di;
    private String name;
    private String use;
    private String color;
    private Integer ddc;
    private String location;
    private String message;
    private Long gatewayId;
    private Integer seq;
    private Integer doId;
    private Integer dcu;
    private String diUse;
    private String doUse;

    public Ddc of(
            Long id,
            TbGateway gateway
    ) {

        return Ddc.builder()
                .id(id)
                .gateway(gateway)
                .location(this.location)
                .color(this.color)
                .use(this.use)
                .di(this.di)
                .name(this.name)
                .message(this.message)
                .ddc(this.ddc)
                .seq(this.seq)
                .doId(this.doId)
                .dcu(this.dcu)
                .diUse(this.diUse)
                .doUse(this.doUse)
                .build();
    }

    public Ddc of(
            TbGateway gateway
    ) {

        return Ddc.builder()
                .id(this.id)
                .gateway(gateway)
                .location(this.location)
                .color(this.color)
                .di(this.di)
                .use(this.use)
                .name(this.name)
                .ddc(this.ddc)
                .seq(this.seq)
                .message(this.message)
                .doId(this.doId)
                .diUse(this.diUse)
                .doUse(this.doUse)
                .build();
    }
}
