package com.sara.project.eletron.dto;

import com.sara.project.ddcmessage.DdcMessage;
import com.sara.project.eletron.Eletron;
import com.sara.project.user.User;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EletronRequestDto {

    private Long id;

    private String ipAddress;

    private Long dcp;


    private Integer inValue;

    private String inType;

    public Eletron of(
            Long id
    ) {

        return Eletron.builder()
                .id(id)
                .ipAddress(this.ipAddress)
                .dcp(this.dcp)
                .inDate(LocalDateTime.now())
                .inValue(this.inValue)
                .inType(this.inType)
                .build();
    }
}
