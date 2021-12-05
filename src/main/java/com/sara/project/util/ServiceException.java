package com.sara.project.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceException extends RuntimeException{
    private String serviceName;
    private String actionName;
    private String errorMsg;

    public ServiceException(String serviceName, String actionName, String errorMsg) {
        super(errorMsg);
        this.serviceName = serviceName;
        this.actionName = actionName;
        this.errorMsg = errorMsg;
    }
}

