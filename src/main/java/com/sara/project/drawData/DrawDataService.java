package com.sara.project.drawData;

import com.sara.project.drawData.dto.*;

import java.util.List;

public interface DrawDataService {
    DrawAllResponseDto getDrawList(Long id,Boolean type);

    List<DrawDataResponseDto> getDrawAll();

    void save(DrawAllRequestDto drawAllRequestDto);

    void delete(Long id);

    List<DrawGroupResponseDto> getDrawGroupAll();

    void dataGroupSave(DrawGroupRequestDto drawDataResponseDtos);

    void dataGroupDelete(Long id);

    void dataGroupUpdate(DrawGroupRequestDto drawGroupRequestDto);

    DrawGroupResponseDto getDrawGroup(Long id);
}
