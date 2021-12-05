package com.sara.project.aram.dto;

import com.sara.project.aram.AramSet;
import com.sara.project.eletron.Eletron;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AramSetRequestDto {

    private Long id;
    private Long aramMaxOne;
    private Long aramMinOne;
    private Boolean aramUseOne;
    private Long aramMinTwo;
    private Long aramMaxTwo;
    private Boolean aramUseTwo;
    private Long aramMinThree;
    private Long aramMaxThree;
    private Boolean aramUseThree;

    public AramSet of(
            Long id
    ) {

        return AramSet.builder()
                .id(id)
        .aramMaxOne(this.aramMaxOne)
        .aramMinOne(this.aramMinOne)
        .aramUseOne(this.aramUseOne)
        .aramMinTwo(this.aramMinTwo)
        .aramMaxTwo(this.aramMaxTwo)
        .aramUseTwo(this.aramUseTwo)
        .aramMinThree(this.aramMinThree)
        .aramMaxThree(this.aramMaxThree)
        .aramUseThree(this.aramUseThree)
                .build();
    }
}
