package com.sara.project.aram.dto;

import com.sara.project.aram.Aram;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AramRequestDto {

    private Long id;
    private String name;
    private LocalDateTime inDate;
    private String status;
    private String confirmYn;
    private Long eletronId;

    public Aram of(
            Long id
    ) {

        return Aram.builder()
                .id(id)
                .name(this.name)
                .status(this.status)
                .inDate(LocalDateTime.now())
                .confirmYn(this.confirmYn)
                .eletronId(this.eletronId)
                .build();
    }
}
