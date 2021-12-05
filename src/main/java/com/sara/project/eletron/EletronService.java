package com.sara.project.eletron;

import com.sara.project.eletron.dto.EletronExcelMapper;
import com.sara.project.eletron.dto.EletronMapper;
import com.sara.project.eletron.dto.EletronRequestDto;
import com.sara.project.eletron.dto.EletronResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EletronService {

    List<EletronResponseDto> getEletronResponseDtoList();
    Page<EletronResponseDto> getEletronPage(Pageable pageRequest);

    List<EletronMapper> getEletronGraphList(Long id);
    List<EletronExcelMapper> getEletronExcelList(Long Id,String std ,String end);
    void save(EletronRequestDto gatewayRequestDto);
    void deleteAll();

    List<EletronMapper> getExcelList(Long id);
}
