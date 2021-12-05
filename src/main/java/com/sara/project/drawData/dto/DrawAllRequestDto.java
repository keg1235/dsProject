package com.sara.project.drawData.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DrawAllRequestDto {

    private String version;
    private DrawDataRequestDto backgroundImage;
    private List<DrawDataRequestDto> objects;
    private Long groupId;
}
