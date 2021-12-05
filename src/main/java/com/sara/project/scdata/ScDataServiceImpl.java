package com.sara.project.scdata;

import com.sara.project.dcp.Dcp;
import com.sara.project.dcp.dto.DcpRequestDto;
import com.sara.project.dcp.dto.DcpResponseDto;
import com.sara.project.gateway.TbGateway;
import com.sara.project.scdata.dto.ScDataRequestDto;
import com.sara.project.scdata.dto.ScDataResponseDto;
import com.sara.project.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScDataServiceImpl implements ScDataService {


    @Autowired
    private ScDataRepository scDataRepository;

    @Override
    public List<ScDataResponseDto> getScDataResponseDtoList() {

        List<ScData> scDataList = scDataRepository.findAll();
        List<ScDataResponseDto> scDataResponseDtos
                = scDataList.stream().map(ScDataResponseDto::of)
                .collect(Collectors.toList());
        return scDataResponseDtos;
    }

    @Override
    public ScDataResponseDto getScDataResponseDto() {

        return null;
    }

    @Override
    public Page<ScDataResponseDto> getScDataPage(Pageable pageRequest) {
        Page<ScData> scDataList = scDataRepository.findAll(pageRequest);

        Page<ScDataResponseDto> scDataResponseDtos = scDataList.map(
                scData -> ScDataResponseDto.of(scData)
        );
        return scDataResponseDtos;
    }

    @Override
    public ScDataResponseDto getScDataResponseDto(Long id) {
        Optional<ScData> scData = scDataRepository.findById(id);
        ScDataResponseDto dcpResponseDto =
                ScDataResponseDto.of(scData.get());

        return dcpResponseDto;
    }

    @Override
    public void save(ScDataRequestDto scDataRequestDto) {
        try {
            Long generatedId = scDataRepository.findMaxValue() + 1;

            ScData scData = scDataRequestDto.of(generatedId);
            scDataRepository.save(scData);


        } catch (Exception e) {
            throw new ServiceException("DcpService", "save", e.toString());
        }

    }

    @Override
    public void update(Long id, ScDataRequestDto scDataRequestDto) {
        try {

            ScData scData = scDataRequestDto.of(id);
            scDataRepository.save(scData);
        } catch (Exception e) {
            throw new ServiceException("DcpService", "update", e.toString());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            scDataRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("DcpService", "delete", e.toString());
        }
    }
}
