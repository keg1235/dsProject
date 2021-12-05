package com.sara.project.eletron;

import com.sara.project.dcp.Dcp;
import com.sara.project.dcp.DcpRepository;
import com.sara.project.eletron.dto.EletronExcelMapper;
import com.sara.project.eletron.dto.EletronMapper;
import com.sara.project.eletron.dto.EletronRequestDto;
import com.sara.project.eletron.dto.EletronResponseDto;
import com.sara.project.gateway.GatewayRepository;
import com.sara.project.gateway.TbGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EletronServiceImpl implements EletronService{

    @Autowired
    private EletronRepository eletronRepository;

    @Autowired
    private DcpRepository dcpRepository;

    @Autowired
    private GatewayRepository gatewayRepository;


    @Override
    public List<EletronResponseDto> getEletronResponseDtoList() {
        List<Eletron> eletronList = eletronRepository.findmonthDay();
        List<EletronResponseDto> eletronResponseDtoList
                = eletronList.stream().map(EletronResponseDto::of)
                .collect(Collectors.toList());
        return eletronResponseDtoList;
    }

    @Override
    public Page<EletronResponseDto> getEletronPage(Pageable pageRequest) {
        Page<Eletron> eletrons = eletronRepository.findAll(pageRequest);

        Page<EletronResponseDto> eletronResponseDtopage = eletrons.map(
                eletron -> EletronResponseDto.of(eletron)
        );
        return eletronResponseDtopage;
    }

    @Override
    public List<EletronMapper> getEletronGraphList(Long id) {
        Dcp dcp = dcpRepository.findByMainYn("YES");
       // TbGateway tbGateway =  gatewayRepository.findById(dcp.getGateway().getId()).orElse(null);

        List<EletronMapper> eletronList = eletronRepository.findGrap(dcp.getGateway().getIpAddress());

        return eletronList;
    }

    @Override
    public List<EletronExcelMapper> getEletronExcelList(Long Id,String std ,String end) {

        Dcp dcp = dcpRepository.findByMainYn("YES");
        //TbGateway tbGateway =  gatewayRepository.findById(dcp.getGateway().getId()).orElse(null);
        List<EletronExcelMapper> eletronList = eletronRepository.findExcelData(dcp.getGateway().getIpAddress(),std,end);

        return eletronList;
    }

    @Override
    public void save(EletronRequestDto eletronRequestDto) {
        Long generatedId = eletronRepository.findMaxValue() + 1;
        Eletron eletron = eletronRequestDto.of(generatedId);
        eletronRepository.save(eletron);
    }

    @Override
    public void deleteAll() {
        eletronRepository.deleteAll();
    }

    @Override
    public List<EletronMapper> getExcelList(Long id) {
        Dcp dcp = dcpRepository.findByMainYn("YES");
        // TbGateway tbGateway =  gatewayRepository.findById(dcp.getGateway().getId()).orElse(null);

        List<EletronMapper> eletronList = eletronRepository.findGrapExcel(dcp.getGateway().getIpAddress());

        return eletronList;
    }
}
