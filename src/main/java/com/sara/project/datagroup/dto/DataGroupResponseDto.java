package com.sara.project.datagroup.dto;

import com.sara.project.datagroup.DataGroup;
import com.sara.project.dcp.Dcp;
import com.sara.project.ddcmessage.ddcMessageDto.DdcMessageResponseDto;
import com.sara.project.ddcmessage.ddcMessageDto.DdcUser;
import com.sara.project.scdata.ScData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DataGroupResponseDto {

    private Long id;
    private Long groupId;
    private String name;
    private Long ddcId;
    private Long dcpId;
    private Long scdataId;
    private Integer sort;
    private String ddcname;


    public static DataGroupResponseDto of(
            DataGroup dataGroup
    ) {

        return DataGroupResponseDto.builder()
                .id(dataGroup.getId())
                .groupId(dataGroup.getGroupId())
                .name(dataGroup.getName())
                .scdataId(dataGroup.getScData() == null ? null :dataGroup.getScData().getId())
                .dcpId(dataGroup.getDcp() == null? null : dataGroup.getDcp().getId())
                .ddcId(dataGroup.getDdcId().getId())
                .ddcname(dataGroup.getDdcId().getName())
                .sort(dataGroup.getSort())

                .build();
    }
}
