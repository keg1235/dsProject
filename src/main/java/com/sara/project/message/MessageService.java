package com.sara.project.message;

import com.sara.project.message.dto.MessageRequestDto;
import com.sara.project.message.dto.MessageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    List<MessageResponseDto> getMessageList();

    Page<MessageResponseDto> getMessagePage(Pageable pageRequest);

    MessageResponseDto get(Long id);

    void save(MessageRequestDto messageRequestDto);

    void update(Long id, MessageRequestDto messageRequestDto);

    void delete(Long id);
}
