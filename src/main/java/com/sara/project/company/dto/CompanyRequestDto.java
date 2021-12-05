package com.sara.project.company.dto;

import com.sara.project.company.Company;
import com.sara.project.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestDto {

    private Long id;
    private String name;
    private String addr;
    private String tel;
    private String useYn;

    public Company of(
            Long id
    ) {

        return Company.builder()
                .id(id)
                .name(this.name)
                .addr(this.addr)
                .tel(this.tel)
                .useYn(this.useYn)
                .build();
    }

    public Company of(

    ) {
        return Company.builder()
                .id(this.id)
                .name(this.name)
                .addr(this.addr)
                .tel(this.tel)
                .useYn(this.useYn)
                .build();
    }
}
