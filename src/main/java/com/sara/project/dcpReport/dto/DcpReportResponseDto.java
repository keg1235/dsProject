package com.sara.project.dcpReport.dto;

import com.sara.project.dcpReport.DcpReport;
import com.sara.project.dcpSet.DcpSet;
import com.sara.project.eletron.Eletron;
import com.sara.project.eletron.dto.EletronResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DcpReportResponseDto {

    private Long id;
    private String ipAddress;
    private String name;
    private Integer dcpNum;
    private String inDate;
    private String inValue;
    private String inType;

    public static DcpReportResponseDto of(
            DcpReport dcpReport
    ) {
        //Long million = dcpReport.getInDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return DcpReportResponseDto.builder()
                .id(dcpReport.getId())
                .name(dcpReport.getName())
                .dcpNum(dcpReport.getDcpNum())
                .ipAddress(dcpReport.getIpAddress())
                .inDate(dcpReport.getInDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .inValue(dcpReport.getInValue())
                .inType(dcpReport.getInType())
                .build();
    }


}
