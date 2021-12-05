package com.sara.project.dcpSet;

import com.sara.project.common.util.CommonCode;
import com.sara.project.common.util.CommonGroupCode;
import com.sara.project.dcp.DcpService;
import com.sara.project.dcpSet.dto.DcpGroupDto;
import com.sara.project.dcpSet.dto.DcpSetRequestDto;
import com.sara.project.dcpSet.dto.DcpSetResponseDto;
import com.sara.project.ddc.Ddc;
import com.sara.project.ddc.dto.DdcRequestDto;
import com.sara.project.ddc.dto.DdcResponseDto;
import com.sara.project.gateway.GatewayRepository;
import com.sara.project.gateway.TbGateway;
import com.sara.project.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DcpSetServiceImpl implements DcpSetService {


    @Autowired
    private DcpSetRepository dcpSetRepository;


    @Autowired
    private GatewayRepository gatewayRepository;

    @Override
    public void update(Long id, DcpSetRequestDto dcpSetRequestDto) {
        try {
            TbGateway gateway = gatewayRepository.findById(dcpSetRequestDto.getGatewayId()).orElse(null);
            DcpSet dcpSet = dcpSetRequestDto.of(id,gateway);
            dcpSetRepository.save(dcpSet);
        } catch (Exception e) {
            throw new ServiceException("DcpSetService", "update", e.toString());
        }
    }


    @Override
    public void delete(Long id) {
        try {
            dcpSetRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("DcpSetService", "delete", e.toString());
        }
    }


    @Transactional
    @Override
    public void save(List<DcpSetRequestDto> dcpSetRequestDtos) {
        try {

            for(DcpSetRequestDto dcpSetRequestDto:dcpSetRequestDtos){
                Long generatedId = dcpSetRepository.findMaxValue() + 1;
                TbGateway gateway = gatewayRepository.findById(dcpSetRequestDto.getGatewayId()).orElse(null);
                DcpSet dcpSet = dcpSetRequestDto.of(generatedId,gateway);

                dcpSetRepository.save(dcpSet);
            }

        } catch (Exception e) {
            throw new ServiceException("DcpSetService", "save", e.toString());
        }
    }

    @Override
    public DcpSetResponseDto getDcpSetResponseDto(Long id) {
        DcpSet dcpset = dcpSetRepository.findById(id).orElse(null);
        DcpSetResponseDto dcpSetResponseDto =
                DcpSetResponseDto.of(dcpset);

        return dcpSetResponseDto;
    }

    @Override
    public List<DcpSetResponseDto> getDcpSetResponseDtoList() {

        List<DcpSet> dcpsetList = dcpSetRepository.findAll();
        List<DcpSetResponseDto> dcpSetResponseDto
                = dcpsetList.stream().map(DcpSetResponseDto::of)
                .collect(Collectors.toList());
        return dcpSetResponseDto;
    }

    @Override
    public List<CommonGroupCode> getDcpGroupResponseDto() {


        List<CommonGroupCode>codes =  dcpSetRepository.findAll()
                .parallelStream()
                .map(dcpSet -> new CommonGroupCode(dcpSet.getDcp().toString(), dcpSet.getDcp().toString(), dcpSet.getGateway().getIpAddress()))
                .collect(
                        Collectors.toMap(
                                sum -> sum.getId(),
                                Function.identity(),
                                (sum1, sum2) -> new CommonGroupCode(
                                        sum1.getId(),
                                        sum1.getId(),
                                        sum1.getGateWayAddr()
                                )
                        )
                )
                .values()
                .stream()
                .sorted(Comparator.comparing(CommonGroupCode::getId))
                .collect(Collectors.toList())
        ;
        /*
        List<com.sara.project.ddc.CommonCode> commonCodes = dcpSetRepository.getGroupDcp();

        List<CommonCode> codes = new ArrayList<>();
        for (com.sara.project.ddc.CommonCode commonCode :  commonCodes){
            CommonCode code = new CommonCode(commonCode.getId(),commonCode.getName());
            codes.add(code);
        }*/

        return codes;
    }

    @Override
    public List<DcpGroupDto> getDcpGroupDto() {


        List<DcpGroupDto> codes =  dcpSetRepository.findAll()
                .parallelStream()
                .map(dcpSet -> new DcpGroupDto(dcpSet.getDcp(), dcpSet.getType().toString(), dcpSet.getGateway().getIpAddress(),dcpSet.getGateway().getPort()))
                .collect(
                        Collectors.toMap(
                                sum -> sum.getId(),
                                Function.identity(),
                                (sum1, sum2) -> new DcpGroupDto(
                                        sum1.getId(),
                                        sum1.getType(),
                                        sum1.getGateWayAddr(),
                                        sum1.getPortAddr()
                                )
                        )
                )
                .values()
                .stream()
                .sorted(Comparator.comparing(DcpGroupDto::getId))
                .collect(Collectors.toList())
                ;

        return codes;
    }


}
