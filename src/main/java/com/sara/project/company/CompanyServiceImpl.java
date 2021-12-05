package com.sara.project.company;

import com.sara.project.company.dto.CompanyRequestDto;
import com.sara.project.company.dto.CompanyResponseDto;
import com.sara.project.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService{


    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void update(Long id, CompanyRequestDto companyRequestDto) {
        try {
            Company company = companyRequestDto.of();
            company.setId(id);
            companyRepository.save(company);
        } catch (Exception e) {
            throw new ServiceException("CompanyService", "update", e.toString());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            companyRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("CompanyService", "delete", e.toString());
        }
    }

    @Override
    public void save(CompanyRequestDto companyRequestDto) {
        try {
            Long generatedId = companyRepository.findMaxValue() + 1;
            Company company = companyRequestDto.of(generatedId);
            companyRepository.save(company);
        } catch (Exception e) {
            throw new ServiceException("CompanyService", "save", e.toString());
        }
    }

    @Override
    public List<CompanyResponseDto> getCompanyList() {
        List<Company> companyList = companyRepository.findAll();
        List<CompanyResponseDto> companyResponseDtoList
                = companyList.stream().map(CompanyResponseDto::of).collect(Collectors.toList());
        return companyResponseDtoList;
    }

    @Override
    public Page<CompanyResponseDto> getCompanyPage(Pageable pageRequest) {
        Page<Company> companys = companyRepository.findAll(pageRequest);

        Page<CompanyResponseDto> companyResponseDtoPage = companys.map(
                company -> CompanyResponseDto.of(company)
        );
        return companyResponseDtoPage;
    }

    @Override
    public CompanyResponseDto get(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        Company company = null;
        if(companyOptional.isPresent()) {
            company = companyOptional.get();
        }
        CompanyResponseDto companyResponseDto = CompanyResponseDto.of(company);
        return companyResponseDto;
    }
}
