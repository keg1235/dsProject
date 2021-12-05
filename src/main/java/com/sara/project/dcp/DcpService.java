package com.sara.project.dcp;

import com.sara.project.dcp.dto.DcpRequestDto;
import com.sara.project.dcp.dto.DcpResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DcpService {
    DcpResponseDto getDcpResponseDto(Long id);

    List<DcpResponseDto> getDcpResponseDtoList();

    Page<DcpResponseDto> getDcpPage(Pageable pageRequest);

    void save(DcpRequestDto dcpRequestDto);

    void update(Long id, DcpRequestDto dcpRequestDto);

    void delete(Long id);
}
