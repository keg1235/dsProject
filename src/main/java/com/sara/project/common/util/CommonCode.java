package com.sara.project.common.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonCode {
    private int id;
    private String name;

    public CommonCode(){}

    public CommonCode(int  id, String name) {
        this.id = id;
        this.name = name;
    }
}
