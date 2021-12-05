package com.sara.project.aram.dto;

import com.sara.project.aram.Aram;
import com.sara.project.eletron.Eletron;
import com.sara.project.eletron.dto.EletronResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AramResponseDto {

    private Long id;
    private String name;
    private String inDate;
    private String status;
    private String confirmYn;
    private Long eletronId;

    public static AramResponseDto of(
            Aram aram
    ) {
        String time = aram.getInDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return AramResponseDto.builder()
                .id(aram.getId())
                .name(aram.getName())
                .status(aram.getStatus())
                .confirmYn(aram.getConfirmYn())
                .eletronId(aram.getEletronId())
                .inDate(time)
                .build();
    }
}
