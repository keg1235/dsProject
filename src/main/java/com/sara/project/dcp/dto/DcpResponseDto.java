package com.sara.project.dcp.dto;

import com.sara.project.dcp.Dcp;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DcpResponseDto {

    private Long id;


    private String location;


    private String name;


    private String memo;


    private Long gateway_id;


    private Integer delayTimer;


    private String useYn;


    private String type;


    private Integer ddc;


    private Integer onSuggest;


    private Integer offSuggest;


    private String onType;

    private String offType;

    private String gatewayadress;

    private Integer saveValue;

    private String mainYn;


    public static DcpResponseDto of(
            Dcp dcp
    ) {

        return DcpResponseDto.builder()
                .id(dcp.getId())
                .ddc(dcp.getDdc())
                .location(dcp.getLocation())
                .memo(dcp.getMemo())
                .name(dcp.getName())
                .gateway_id(dcp.getGateway().getId())
                .gatewayadress(dcp.getGateway().getIpAddress())
                .delayTimer(dcp.getDelayTimer())
                .useYn(dcp.getUseYn())
                .type(dcp.getType())
                .onSuggest(dcp.getOnSuggest())
                .offSuggest(dcp.getOffSuggest())
                .onType(dcp.getOnType())
                .offType(dcp.getOffType())
                .saveValue(dcp.getSaveValue())
                .mainYn(dcp.getMainYn())
                .build();
    }
}
