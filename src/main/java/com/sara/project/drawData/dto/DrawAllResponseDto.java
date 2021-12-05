package com.sara.project.drawData.dto;

import com.sara.project.drawData.DrawData;
import lombok.*;

import java.util.List;

@Getter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DrawAllResponseDto {

    private String version;
    private DrawDataResponseDto backgroundImage;
    private List<DrawDataResponseDto> objects;
    private Long groupId;

    public static DrawAllResponseDto of(
            DrawDataResponseDto backgroundImage,
            List<DrawDataResponseDto> objects,
            Long groupId
    ) {

        return DrawAllResponseDto.builder()
                .version("4.6.0")
                .groupId(groupId)
                .backgroundImage(backgroundImage)
                .objects(objects)
                .build();
    }
}
