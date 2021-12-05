package com.sara.project.message;

import com.sara.project.ddc.Ddc;
import com.sara.project.ddc.DdcRepository;
import com.sara.project.message.dto.MessageRequestDto;
import com.sara.project.message.dto.MessageResponseDto;
import com.sara.project.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private DdcRepository ddcRepository;


    @Override
    public List<MessageResponseDto> getMessageList() {
        List<Message> messageList = messageRepository.findAll();
        List<MessageResponseDto> messageResponseDtoList
                = messageList.stream().map(MessageResponseDto::of).collect(Collectors.toList());
        return messageResponseDtoList;
    }

    @Override
    public Page<MessageResponseDto> getMessagePage(Pageable pageRequest) {
        Page<Message> messages = messageRepository.findAll(pageRequest);

        Page<MessageResponseDto> messageResponseDtoPage = messages.map(
                message -> MessageResponseDto.of(message)
        );
        return messageResponseDtoPage;
    }

    @Override
    public MessageResponseDto get(Long id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        Message message = null;
        if(messageOptional.isPresent()) {
            message = messageOptional.get();
        }
        MessageResponseDto messageResponseDto = MessageResponseDto.of(message);
        return messageResponseDto;
    }

    @Override
    public void save(MessageRequestDto messageRequestDto) {
        try {
            Long generatedId = messageRepository.findMaxValue() + 1;
            Optional<Ddc> ddc =ddcRepository.findById(messageRequestDto.getDdcId());
            if (ddc.isPresent()){
                Message message = messageRequestDto.of(generatedId,ddc.get());
                messageRepository.save(message);
            }else{
                throw new ServiceException("MessageService", "ddc정보가 잘못되었습니다.", "");
            }

        } catch (Exception e) {
            throw new ServiceException("MessageService", "save", e.toString());
        }
    }

    @Override
    public void update(Long id, MessageRequestDto messageRequestDto) {
        try {
            Optional<Ddc> ddc =ddcRepository.findById(messageRequestDto.getDdcId());
            if (ddc.isPresent()) {
                Message message = messageRequestDto.of(ddc.get());
                messageRepository.save(message);
            }else{
                throw new ServiceException("MessageService", "ddc정보가 잘못되었습니다.", "");
            }

        } catch (Exception e) {
            throw new ServiceException("MessageService", "update", e.toString());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            messageRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("MessageService", "delete", e.toString());
        }
    }
}
