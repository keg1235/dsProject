package com.sara.project.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseMessage {
    private int code;
    private Object data;
    private String message;

    public ResponseMessage(){}

    public ResponseMessage(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}
