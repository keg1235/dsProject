package com.sara.project.eletron.dto;

import com.sara.project.dcp.Dcp;
import com.sara.project.eletron.Eletron;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EletronResponseDto {

    private Long id;
    private String ipAddress;
    private Long dcp;
    private String inDate;
    private Integer inValue;
    private String inType;
    private BigDecimal total;
    private Long milliseconds;

    public static EletronResponseDto of(
            Eletron eletron
    ) {
        Long million = eletron.getInDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return EletronResponseDto.builder()
                .id(eletron.getId())
                .dcp(eletron.getDcp())
                .ipAddress(eletron.getIpAddress())
                .inDate(eletron.getInDate().toString())
                .inValue(eletron.getInValue())
                .inType(eletron.getInType())
                .total(eletron.getNowTotal())
                .milliseconds(million)
                .build();
    }
}
