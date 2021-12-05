package com.sara.project.company;

import com.sara.project.company.dto.CompanyRequestDto;
import com.sara.project.company.dto.CompanyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    void update(Long id, CompanyRequestDto companyRequestDto);

    void delete(Long id);

    void save(CompanyRequestDto companyRequestDto);

    List<CompanyResponseDto> getCompanyList();

    Page<CompanyResponseDto> getCompanyPage(Pageable pageRequest);

    CompanyResponseDto get(Long id);
}
