package com.sara.project.datagroup.dto;

import com.sara.project.datagroup.DataGroup;
import com.sara.project.dcp.Dcp;
import com.sara.project.ddc.Ddc;
import com.sara.project.ddcmessage.DdcMessage;
import com.sara.project.scdata.ScData;
import com.sara.project.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Id;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataGroupRequestDto {

    private Long groupId;
    private String name;
    private Long ddcId;
    private Integer sort;
    private Long dcpid;
    private Long scdataid;



    public DataGroup of(
            Long id,
            Long groupid,
            Ddc ddc,
            Dcp dcp,
            ScData scData
    ) {

        return DataGroup.builder()
                .id(id)
                .ddcId(ddc)
                .groupId(groupid)
                .dcp(dcp)
                .scData(scData)
                .name(this.name)
                .sort(this.sort)
                .build();
    }
}
