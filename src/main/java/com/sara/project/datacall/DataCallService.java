package com.sara.project.datacall;

import com.sara.project.ddc.dto.DdcResponseDto;

import java.util.List;

public interface DataCallService {

    List<DdcResponseDto> datacall();
    void stateChek();
    void dataOnOff(DataCallRequest dataCallRequest);

    void dataOnOffMulti(List<DataCallRequest> dataCallRequest);
    void dataOnOffMultiCust(List<DataCallRequest> dataCallRequest);
}
