package com.sara.project.gateway.dto;


import com.sara.project.gateway.TbGateway;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class GatewayResponseDto {
    private Long id;
    private String ipAddress;
    private String location;
    private String memo;
    private Integer port;

    public static GatewayResponseDto of(
            TbGateway gateway
    ) {
        return GatewayResponseDto.builder()
                .id(gateway.getId())
                .ipAddress(gateway.getIpAddress())
                .location(gateway.getLocation())
                .memo(gateway.getMemo())
                .port(gateway.getPort())
                .build();
    }
}
