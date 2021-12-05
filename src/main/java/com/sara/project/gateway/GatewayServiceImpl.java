package com.sara.project.gateway;

import com.sara.project.gateway.dto.GatewayRequestDto;
import com.sara.project.gateway.dto.GatewayResponseDto;
import com.sara.project.util.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class GatewayServiceImpl implements GatewayService {

    @Autowired
    private GatewayRepository gatewayRepository;

    @Override
    public List<GatewayResponseDto> getGatewayResponseDtoList() {
        List<TbGateway> gatewayList = gatewayRepository.findAll();
        List<GatewayResponseDto> gatewayResponseDtoList
                = gatewayList.stream().map(GatewayResponseDto::of)
                .collect(Collectors.toList());
        return gatewayResponseDtoList;
    }

    @Override
    public GatewayResponseDto getGatewayResponseDto() {
        GatewayResponseDto gatewayResponseDto = null;
        TbGateway gateway = gatewayRepository.findById(1l).orElseGet(null);
        if (gateway != null){
            gatewayResponseDto = GatewayResponseDto.of(gateway);
        }
        return gatewayResponseDto;
    }

    @Override
    public Page<GatewayResponseDto> getGatewayPage(Pageable pageRequest) {
        Page<TbGateway> gateways = gatewayRepository.findAll(pageRequest);

        Page<GatewayResponseDto> gatewayResponseDtopage = gateways.map(
                gateway -> GatewayResponseDto.of(gateway)
        );
        return gatewayResponseDtopage;
    }

    @Override
    public GatewayResponseDto getGatewayResponseDto(Long id) {

        GatewayResponseDto gatewayResponseDto = null;
        Optional<TbGateway> gateway = gatewayRepository.findById(id);
        if (gateway.isPresent()){
            gatewayResponseDto = GatewayResponseDto.of(gateway.get());
        }else{
            TbGateway gateway1 = new TbGateway();
            gatewayResponseDto = GatewayResponseDto.of(gateway1);
        }
        return gatewayResponseDto;
    }

    @Override
    public void save(GatewayRequestDto gatewayRequestDto) {
        try {
            Long generatedId = gatewayRepository.findMaxValue() + 1;
            TbGateway gateway = gatewayRequestDto.of();
            log.info("{}",gatewayRequestDto);
            if(gateway.getId() == null){
                gateway.setId(generatedId);
            }
            gatewayRepository.save(gateway);

        } catch (Exception e) {
            throw new ServiceException("GatewayService", "save", e.toString());
        }
    }

    @Override
    public void update(Long id, GatewayRequestDto gatewayRequestDto) {
        try {
            TbGateway gateway = gatewayRequestDto.of(id);
            gatewayRepository.save(gateway);
        } catch (Exception e) {
            throw new ServiceException("GatewayService", "update", e.toString());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            gatewayRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("GatewayService", "delete", e.toString());
        }
    }
}
