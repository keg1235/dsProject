package com.sara.project.common.util;


import lombok.*;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ErrResult {
    Boolean errYn;
    String  errResult;

    public ErrResult of(
            Boolean errYn ,String errResult
    ){
    return ErrResult.builder()
            .errYn(errYn)
            .errResult(errResult)
            .build();
    }
}
