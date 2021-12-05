package com.sara.project.dcpSet.dto;

import com.sara.project.dcp.Dcp;
import com.sara.project.dcpSet.DcpSet;
import com.sara.project.gateway.TbGateway;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DcpSetRequestDto {

    private Long id;
    private String location;
    private String name;
    private String memo;
    private Integer dcp; //기기 번호
    private Long gatewayId;
    private Integer num; //기기 위치
    private Integer address; //메모리 주소
    private String type; //디지털 (온도) ,노말(di ,do)
    private String readWriteType; // write 인지
    private String dataType; // double , Long , word 3가지
    private String volumeMin;
    private String volumeMax;
    private String displayMin;
    private String displayMax;
    private String unit;
    private Integer indexNum;
    private String dcpSend;


    public DcpSet of(
            Long id,
            TbGateway tbGateway
    ) {

        return DcpSet.builder()
                .id(id)
                .location(this.location)
                .name(this.name)
                .memo(this.memo)
                .dcp(this.dcp)
                .gateway(tbGateway)
                .num(this.num)
                .address(this.address)
                .type(this.type)
                .readWriteType(this.readWriteType)
                .dataType(this.dataType)
                .volumeMin(this.volumeMin)
                .volumeMax(this.volumeMax)
                .displayMin(this.displayMin)
                .displayMax(this.displayMax)
                .unit(this.unit)
                .indexNum(this.indexNum)
                .dcpSend(this.dcpSend)
                .build();
    }

    public DcpSet of(
            TbGateway tbGateway
    ) {
        return DcpSet.builder()
                .id(this.id)
                .location(this.location)
                .name(this.name)
                .memo(this.memo)
                .dcp(this.dcp)
                .gateway(tbGateway)
                .num(this.num)
                .address(this.address)
                .type(this.type)
                .readWriteType(this.readWriteType)
                .dataType(this.dataType)
                .volumeMin(this.volumeMin)
                .volumeMax(this.volumeMax)
                .displayMin(this.displayMin)
                .displayMax(this.displayMax)
                .unit(this.unit)
                .dcpSend(this.dcpSend)
                .indexNum(this.indexNum)
                .build();
    }
}
