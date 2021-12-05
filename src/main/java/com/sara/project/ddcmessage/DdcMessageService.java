package com.sara.project.ddcmessage;

import com.sara.project.ddcmessage.ddcMessageDto.DdcMessageRequestDto;
import com.sara.project.ddcmessage.ddcMessageDto.DdcMessageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DdcMessageService {
    List<DdcMessageResponseDto> getDdcMessageResponseDtoList();
    DdcMessageResponseDto  getDdcMessageResponseDto();
    Page<DdcMessageResponseDto> getDdcMessagePage(Pageable pageRequest);
    DdcMessageResponseDto getDdcMessageResponseDto(Long id);
    void save(DdcMessageRequestDto ddcMessageRequestDto);
    void update(Long id, DdcMessageRequestDto ddcMessageRequestDto);
    void delete(Long id);

    List<DdcMessageResponseDto> getDdcMessageResponseDtos(Integer id);

    void listsave(List<DdcMessageRequestDto> ddcRequestDtos);
}
