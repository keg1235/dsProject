package com.sara.project.dcpSet;

import com.sara.project.common.util.CommonCode;
import com.sara.project.common.util.CommonGroupCode;
import com.sara.project.dcpSet.dto.DcpGroupDto;
import com.sara.project.dcpSet.dto.DcpSetRequestDto;
import com.sara.project.dcpSet.dto.DcpSetResponseDto;
import com.sara.project.ddc.dto.DdcRequestDto;
import com.sara.project.ddc.dto.DdcResponseDto;

import java.util.List;

public interface DcpSetService {

    void update(Long id, DcpSetRequestDto dcpSetRequestDto);

    void delete(Long id);

    void save(List<DcpSetRequestDto> dcpSetRequestDtos);

    DcpSetResponseDto getDcpSetResponseDto(Long id);

    List<DcpSetResponseDto> getDcpSetResponseDtoList();

    List<CommonGroupCode> getDcpGroupResponseDto();

    List<DcpGroupDto> getDcpGroupDto();
}
