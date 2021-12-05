package com.sara.project.drawData.dto;

import com.sara.project.drawData.DrawData;
import com.sara.project.drawData.DrawGroup;
import lombok.*;

@Getter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DrawGroupResponseDto {

    private Long id;

    private String name;

    private String useYn;
    public static DrawGroupResponseDto of(
            DrawGroup drawGroup
    ) {
        // log.info("{}",drawData);
        return DrawGroupResponseDto.builder()
                .id(drawGroup.getId())
                .name(drawGroup.getName())
                .useYn(drawGroup.getUseYn())
                .build();
    }
}
