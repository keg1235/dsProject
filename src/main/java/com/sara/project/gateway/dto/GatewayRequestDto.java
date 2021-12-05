package com.sara.project.gateway.dto;

import com.sara.project.gateway.TbGateway;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GatewayRequestDto {

    private Long id;
    private String ipAddress;
    private String location;
    private String memo;
    private Integer port;

    public TbGateway of(Long id) {
        return TbGateway.builder()
                .id(id)
                .ipAddress(this.ipAddress)
                .location(this.location)
                .memo(this.memo)
                .port(this.port)
                .build();
    }

    public TbGateway of() {
        return TbGateway.builder()
                .id(this.id)
                .ipAddress(this.ipAddress)
                .location(this.location)
                .port(this.port)
                .memo(this.memo)
                .build();
    }
}
