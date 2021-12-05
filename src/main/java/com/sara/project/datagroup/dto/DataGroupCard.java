package com.sara.project.datagroup.dto;

import com.sara.project.dcp.Dcp;
import com.sara.project.scdata.ScData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DataGroupCard {

    private Integer groupId;
    private String name;
    private Long dcpId;
    private Long scdataId;
    private Integer sort;
    private String dcpName;
    private String scName;
    private String useYn;


    public static DataGroupCard of(
            DataGroupMapper dataGroupMapper,
            ScData scData,
            Dcp dcp
    ) {
        String use = dataGroupMapper.getScdataid() != null ? scData.getUseYn(): dataGroupMapper.getDcpid() != null ? dcp.getUseYn() : null;
        return DataGroupCard.builder()
                .groupId(dataGroupMapper.getGroupid())
                .name(dataGroupMapper.getName())
                .scdataId(dataGroupMapper.getScdataid() )
                .dcpId(dataGroupMapper.getDcpid())
                .sort(dataGroupMapper.getSort())
                .dcpName(dcp.getName())
                .scName(scData.getName())
                .useYn(use)
                .build();
    }


}
