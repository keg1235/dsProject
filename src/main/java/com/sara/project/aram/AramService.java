package com.sara.project.aram;

import com.sara.project.aram.dto.AramRequestDto;
import com.sara.project.aram.dto.AramResponseDto;
import com.sara.project.aram.dto.AramSetRequestDto;
import com.sara.project.aram.dto.AramSetResponseDto;

import java.util.List;

public interface AramService
{
    List<AramResponseDto> getAramList();

    List<AramResponseDto> getAramExcelList();

    AramSetResponseDto getAramSetList();

    void save(AramSetRequestDto aramSetRequestDto);

    void saveStatus(AramRequestDto aramRequestDto);

    void saveClose(AramRequestDto aramRequestDto);
}
