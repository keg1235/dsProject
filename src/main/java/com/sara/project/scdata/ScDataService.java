package com.sara.project.scdata;

import com.sara.project.scdata.dto.ScDataRequestDto;
import com.sara.project.scdata.dto.ScDataResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScDataService {

    List<ScDataResponseDto> getScDataResponseDtoList();
    ScDataResponseDto  getScDataResponseDto();
    Page<ScDataResponseDto> getScDataPage(Pageable pageRequest);

    ScDataResponseDto getScDataResponseDto(Long id);

    void save(ScDataRequestDto gatewayRequestDto);

    void update(Long id, ScDataRequestDto gatewayRequestDto);

    void delete(Long id);
}
