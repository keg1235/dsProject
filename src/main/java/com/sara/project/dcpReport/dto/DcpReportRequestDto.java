package com.sara.project.dcpReport.dto;

import com.sara.project.dcpReport.DcpReport;
import com.sara.project.dcpSet.DcpSet;
import com.sara.project.eletron.Eletron;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DcpReportRequestDto {

    private Long id;
    private LocalDateTime inDate;
    private String inValue;
    private String inType;
    private String ipAddress;
    private String name;
    private Integer dcpNum;

    public DcpReport of(
            Long id
    ) {

        return DcpReport.builder()
                .id(id)
                .ipAddress(this.ipAddress)
                .inDate(LocalDateTime.now())
                .inValue(this.inValue)
                .inType(this.inType)
                .name(this.name)
                .dcpNum(this.dcpNum)
                .build();
    }

    public DcpReport of(Long id,DcpSet dcpSet) {

        return DcpReport.builder()
                .id(id)
                .ipAddress(dcpSet.getGateway().getIpAddress())
                .inDate(LocalDateTime.now())
                .inValue(dcpSet.getValue())
                .inType(dcpSet.getType())
                .name(dcpSet.getName())
                .dcpNum(dcpSet.getAddress())
                .build();

    }
}
