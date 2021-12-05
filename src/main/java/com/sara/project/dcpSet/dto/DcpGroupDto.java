package com.sara.project.dcpSet.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DcpGroupDto {
    private int id;
    private String type;
    private String gateWayAddr;
    private Integer portAddr;

    public DcpGroupDto(){}

    public DcpGroupDto(int  id,String  type,String gateWay, Integer portAddr) {
        this.id = id;
        this.type = type;
        this.gateWayAddr = gateWay;
        this.portAddr = portAddr;
    }
}
