package com.sara.project.common.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonGroupCode {
    private String id;
    private String name;
    private String gateWayAddr;

    public CommonGroupCode(){}

    public CommonGroupCode(String  id, String name,String gateWay) {
        this.id = id;
        this.name = name;
        this.gateWayAddr = gateWay;
    }
}
