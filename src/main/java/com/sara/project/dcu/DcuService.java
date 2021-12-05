package com.sara.project.dcu;

import com.sara.project.dcu.dto.DcuRequestDto;
import com.sara.project.dcu.dto.DcuResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DcuService {

    DcuResponseDto getDcuResponseDto(Long id);

    List<DcuResponseDto> getDcuResponseDtoList();

    Page<DcuResponseDto> getDcuPage(Pageable pageRequest);

    void save(DcuRequestDto dcpRequestDto);

    void update(Long id, DcuRequestDto dcpRequestDto);

    void delete(Long id);
}
