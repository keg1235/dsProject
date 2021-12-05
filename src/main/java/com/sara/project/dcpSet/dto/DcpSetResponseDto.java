package com.sara.project.dcpSet.dto;

import com.sara.project.dcp.Dcp;
import com.sara.project.dcp.dto.DcpResponseDto;
import com.sara.project.dcpSet.DcpSet;
import lombok.*;

@Getter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DcpSetResponseDto {

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
    private String gatewayName;
    private Integer indexNum;
    private String value;
    private String  dcpSend;


    public static DcpSetResponseDto of(
            DcpSet dcpset
    ) {
        return DcpSetResponseDto.builder()
                .id(dcpset.getId())
                .location(dcpset.getLocation())
                .name(dcpset.getName())
                .memo(dcpset.getMemo())
                .dcp(dcpset.getDcp())
                .gatewayId(dcpset.getGateway().getId())
                .num(dcpset.getNum())
                .address(dcpset.getAddress())
                .type(dcpset.getType())
                .readWriteType(dcpset.getReadWriteType())
                .dataType(dcpset.getDataType())
                .volumeMin(dcpset.getVolumeMin())
                .volumeMax(dcpset.getVolumeMax())
                .displayMin(dcpset.getDisplayMin())
                .displayMax(dcpset.getDisplayMax())
                .gatewayName(dcpset.getGateway().getIpAddress())
                .unit(dcpset.getUnit())
                .indexNum(dcpset.getIndexNum())
                .value(dcpset.getValue())
                .dcpSend(dcpset.getDcpSend())
                .build();
    }
}
