package com.sara.project.aram.dto;

import com.sara.project.aram.Aram;
import com.sara.project.aram.AramSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.format.DateTimeFormatter;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AramSetResponseDto {

    private Long id;
    private Long aramMinOne;
    private Long aramMaxOne;
    private Boolean aramUseOne;
    private Long aramMinTwo;
    private Long aramMaxTwo;
    private Boolean aramUseTwo;
    private Long aramMinThree;
    private Long aramMaxThree;
    private Boolean aramUseThree;


    public static AramSetResponseDto of(
            AramSet aramSet
    ) {
        return AramSetResponseDto.builder()
                .aramMinOne(aramSet.getAramMinOne())
                .aramMaxOne(aramSet.getAramMaxOne())
                .aramUseOne(aramSet.getAramUseOne())
                .aramMinTwo(aramSet.getAramMinTwo())
                .aramMaxTwo(aramSet.getAramMaxTwo())
                .aramUseTwo(aramSet.getAramUseTwo())
                .aramMinThree(aramSet.getAramMinThree())
                .aramMaxThree(aramSet.getAramMaxThree())
                .aramUseThree(aramSet.getAramUseThree())
                .build();
    }
}
