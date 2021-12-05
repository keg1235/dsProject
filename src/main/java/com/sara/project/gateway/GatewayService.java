package com.sara.project.gateway;

import com.sara.project.gateway.dto.GatewayRequestDto;
import com.sara.project.gateway.dto.GatewayResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GatewayService {

    List<GatewayResponseDto> getGatewayResponseDtoList();
    GatewayResponseDto  getGatewayResponseDto();
    Page<GatewayResponseDto> getGatewayPage(Pageable pageRequest);

    GatewayResponseDto getGatewayResponseDto(Long id);

    void save(GatewayRequestDto gatewayRequestDto);

    void update(Long id, GatewayRequestDto gatewayRequestDto);

    void delete(Long id);
}
