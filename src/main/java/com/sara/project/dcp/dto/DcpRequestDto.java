package com.sara.project.dcp.dto;

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
public class DcpRequestDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "location")
    private String location;

    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "memo")
    private String memo;

    @ApiModelProperty(value = "gateway_id")
    private Long gateway_id;

    @ApiModelProperty(value = "delayTimer")
    private Integer delayTimer;

    @ApiModelProperty(value = "useYn")
    private String useYn;

    @ApiModelProperty(value = "type")
    private String type;

    @ApiModelProperty(value = "ddc")
    private Integer ddc;

    @ApiModelProperty(value = "onSuggest")
    private Integer onSuggest;

    @ApiModelProperty(value = "offSuggest")
    private Integer offSuggest;

    @ApiModelProperty(value = "onType")
    private String onType;

    @ApiModelProperty(value = "offType")
    private String offType;

    @ApiModelProperty(value = "saveValue")
    private Integer saveValue;

    private String mainYn;


    public Dcp of(
            Long id,
            TbGateway tbGateway
    ) {

        return Dcp.builder()
                .id(id)
                .ddc(this.ddc)
                .location(this.location)
                .memo(this.memo)
                .name(this.name)
                .gateway(tbGateway)
                .delayTimer(this.delayTimer)
                .useYn(this.useYn)
                .type(this.type)
                .onSuggest(this.onSuggest)
                .offSuggest(this.offSuggest)
                .onType(this.onType)
                .offType(this.offType)
                .saveValue(this.saveValue)
                .mainYn(this.mainYn)
                .build();
    }

    public Dcp of(
            TbGateway tbGateway
    ) {

        return Dcp.builder()
                .id(this.id)
                .ddc(this.ddc)
                .location(this.location)
                .memo(this.memo)
                .name(this.name)
                .gateway(tbGateway)
                .delayTimer(this.delayTimer)
                .useYn(this.useYn)
                .type(this.type)
                .onSuggest(this.onSuggest)
                .offSuggest(this.offSuggest)
                .onType(this.onType)
                .offType(this.offType)
                .saveValue(this.saveValue)
                .mainYn(this.mainYn)
                .build();
    }
}
