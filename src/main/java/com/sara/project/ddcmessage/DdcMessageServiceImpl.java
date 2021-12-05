package com.sara.project.ddcmessage;

import com.sara.project.ddc.Ddc;
import com.sara.project.ddc.DdcRepository;
import com.sara.project.ddc.dto.DdcMessageResponse;
import com.sara.project.ddcmessage.ddcMessageDto.DdcMessageRequestDto;
import com.sara.project.ddcmessage.ddcMessageDto.DdcMessageResponseDto;
import com.sara.project.ddcmessage.ddcMessageDto.DdcUser;
import com.sara.project.user.User;
import com.sara.project.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DdcMessageServiceImpl implements  DdcMessageService{

    @Autowired
    private DdcMessageRepository ddcMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DdcRepository ddcRepository;

    @Override
    public List<DdcMessageResponseDto> getDdcMessageResponseDtoList() {
        return null;
    }

    @Override
    public DdcMessageResponseDto getDdcMessageResponseDto() {
        return null;
    }

    @Override
    public Page<DdcMessageResponseDto> getDdcMessagePage(Pageable pageRequest) {
        return null;
    }

    @Override
    public DdcMessageResponseDto getDdcMessageResponseDto(Long id) {
        return null;
    }

    @Override
    public void save(DdcMessageRequestDto ddcMessageRequestDto) {

    }

    @Override
    public void update(Long id, DdcMessageRequestDto ddcMessageRequestDto) {

    }
    @Transactional
    @Override
    public void delete(Long id) {
        ddcMessageRepository.deleteByDdcId(id);
    }

    @Override
    public List<DdcMessageResponseDto> getDdcMessageResponseDtos(Integer id) {
        List<DdcUser> ddcMessages= ddcMessageRepository.findDi(id);
        List<DdcMessageResponseDto> list = new ArrayList<>();
        for (DdcUser ddcUser : ddcMessages){
            DdcMessageResponseDto ddcMessageResponse = new DdcMessageResponseDto(ddcUser.getId(),ddcUser.getDdc(),ddcUser.getName(),ddcUser.getEmail(),ddcUser.getPhone(), ddcUser.getDi());
            list.add(ddcMessageResponse);
        }
        return list;
    }

    @Transactional
    @Override
    public void listsave(List<DdcMessageRequestDto> ddcRequestDtos) {
        ddcMessageRepository.deleteByDdcId(ddcRequestDtos.get(0).getDdcId());
        for (DdcMessageRequestDto ddcMessageRequestDto :  ddcRequestDtos){
            User user = userRepository.findByEmail(ddcMessageRequestDto.getEmail());
            System.out.println(user.getEmail());
            Long generatedId = ddcMessageRepository.findMaxValue() + 1;
            System.out.println(generatedId);
            DdcMessage ddcMessage = ddcMessageRequestDto.of(generatedId,user);
            ddcMessageRepository.save(ddcMessage);
        }

    }
}
