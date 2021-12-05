package com.sara.project.datacall;


import lombok.*;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataCallRequest {

    private Long id;
    private Integer onoffstatus;
    private Integer ddc;
    private Integer dcu;
    private Integer doId;

}
