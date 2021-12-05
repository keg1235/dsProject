package com.sara.project.company.dto;

import com.sara.project.company.Company;
import com.sara.project.user.User;
import com.sara.project.user.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDto {

    private Long id;
    private String name;
    private String addr;
    private String tel;
    private String useYn;

    public static CompanyResponseDto of(
            Company company
    ) {
        return CompanyResponseDto.builder()
                .id(company.getId())
                .name(company.getName())
                .addr(company.getAddr())
                .tel(company.getTel())
                .useYn(company.getUseYn())
                .build();
    }
}
