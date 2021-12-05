package com.sara.project.datagroup;

import com.sara.project.datagroup.dto.DataGroupCard;
import com.sara.project.datagroup.dto.DataGroupMapper;
import com.sara.project.datagroup.dto.DataGroupRequestDto;
import com.sara.project.datagroup.dto.DataGroupResponseDto;
import com.sara.project.ddcmessage.ddcMessageDto.DdcMessageRequestDto;

import java.util.List;

public interface DataGroupService {
    void listsave(List<DataGroupRequestDto> dataGroupRequestDtos);

    void delete(long id);

    List<DataGroupResponseDto> getDataGroupList(Long id);

    List<DataGroupMapper> getDataGroupLists();

    List<DataGroupCard> getDataGroupCard();
}
