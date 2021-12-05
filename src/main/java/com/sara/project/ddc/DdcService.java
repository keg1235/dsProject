package com.sara.project.ddc;

import com.sara.project.common.util.CommonCode;
import com.sara.project.common.util.CommonGroupCode;
import com.sara.project.dcp.dto.DcpRequestDto;
import com.sara.project.ddc.dto.DdcRequestDto;
import com.sara.project.ddc.dto.DdcResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DdcService {
    DdcResponseDto getDdcResponseDto(Long id);

    void save(List<DdcRequestDto> gatewayRequestDtos);

    List<DdcResponseDto> getDdcResponseDtoList();

    Page<DdcResponseDto> getDdcPage(Pageable pageRequest);

    void update(Long id, DdcRequestDto ddcRequestDto);

    void delete(Long id);

    List<CommonCode> getDiResponseDto(Integer id);
    List<CommonCode> getDdcGroupResponseDto();
    List<CommonCode> getDcuGroupResponseDto();
    List<CommonGroupCode> getDcuGroupResponseDtoCust();
    List<CommonGroupCode> getDdcGroupResponseDtoCust();

}
