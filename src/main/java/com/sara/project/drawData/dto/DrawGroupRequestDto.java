package com.sara.project.drawData.dto;

import com.sara.project.drawData.DrawData;
import com.sara.project.drawData.DrawGroup;
import com.sara.project.drawData.Shadow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DrawGroupRequestDto {

    private Long id;
    private String name;
    private String useYn;

    public DrawGroup of(
            Long id
    ) {
        return DrawGroup.builder()
                .id(id)
                .name(this.name)
                .useYn(this.useYn)
                .build();
    }

    public DrawGroup of(

    ) {
        return DrawGroup.builder()
                .id(this.id)
                .name(this.name)
                .useYn(this.useYn)
                .build();
    }
}
