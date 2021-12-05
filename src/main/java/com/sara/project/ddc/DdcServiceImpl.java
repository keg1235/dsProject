package com.sara.project.ddc;

import com.sara.project.common.util.CommonCode;
import com.sara.project.common.util.CommonGroupCode;
import com.sara.project.dcp.dto.DcpRequestDto;
import com.sara.project.ddc.dto.DdcRequestDto;
import com.sara.project.ddc.dto.DdcResponseDto;
import com.sara.project.gateway.GatewayRepository;
import com.sara.project.gateway.TbGateway;
import com.sara.project.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DdcServiceImpl implements DdcService {


    @Autowired
    private DdcRepository ddcRepository;

    @Autowired
    private GatewayRepository gatewayRepository;

    @Override
    public List<DdcResponseDto> getDdcResponseDtoList() {
        List<Ddc> dcpList = ddcRepository.findAll();
        List<DdcResponseDto> dcpResponseDtoList
                = dcpList.stream().map(DdcResponseDto::of)
                .collect(Collectors.toList());
        return dcpResponseDtoList;
    }

    @Override
    public DdcResponseDto getDdcResponseDto(Long id) {
        Optional<Ddc> dcp = ddcRepository.findById(id);
        DdcResponseDto dcpResponseDto =
                DdcResponseDto.of(dcp.get());

        return dcpResponseDto;
    }

    @Override
    public Page<DdcResponseDto> getDdcPage(Pageable pageRequest) {
        Page<Ddc> dcps = ddcRepository.findAll(pageRequest);

        Page<DdcResponseDto> ddcResponseDtopage = dcps.map(
                dcp -> DdcResponseDto.of(dcp)
        );
        return ddcResponseDtopage;
    }

    @Transactional
    @Override
    public void save(List<DdcRequestDto> ddcRequestDtos) {
        try {

            for(DdcRequestDto ddcRequestDto:ddcRequestDtos){
                Long generatedId = ddcRepository.findMaxValue() + 1;
                Optional<TbGateway> gateway = gatewayRepository.findById(ddcRequestDto.getGatewayId());
                Ddc ddc = ddcRequestDto.of(generatedId,gateway.get());
                if(ddcRequestDto.getDi() == null){
                    ddc.setDiUse("NO");
                }else{
                    ddc.setDiUse("YES");
                }

                if(ddcRequestDto.getDoId() == null){
                    ddc.setDoUse("NO");
                }else{
                    ddc.setDoUse("YES");
                }
                ddcRepository.save(ddc);
            }

        } catch (Exception e) {
            throw new ServiceException("DdcService", "save", e.toString());
        }
    }



    @Override
    public void update(Long id, DdcRequestDto ddcRequestDto) {
        try {
            Optional<TbGateway> gateway = gatewayRepository.findById(ddcRequestDto.getGatewayId());
            Ddc ddc = ddcRequestDto.of(id,gateway.get());
            Ddc ddc2 = ddcRepository.findById(id).orElse(null);
            if (ddc2 != null){
                ddc.setDiStatus(ddc2.getDiStatus());
                ddc.setDoStatus(ddc2.getDoStatus());
            }
            if(ddcRequestDto.getDi() == null){
                ddc.setDiUse("NO");
            }else{
                ddc.setDiUse("YES");
            }

            if(ddcRequestDto.getDoId() == null){
                ddc.setDoUse("NO");
            }else{
                ddc.setDoUse("YES");
            }

            ddcRepository.save(ddc);
        } catch (Exception e) {
            throw new ServiceException("DdcService", "update", e.toString());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            ddcRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("DdcService", "delete", e.toString());
        }
    }

    @Override
    public List<CommonCode> getDdcGroupResponseDto() {

        List<com.sara.project.ddc.CommonCode> commonCodes = ddcRepository.getGroupDdc();

        List<CommonCode> codes = new ArrayList<>();
        for (com.sara.project.ddc.CommonCode commonCode :  commonCodes){
            CommonCode code = new CommonCode(commonCode.getId(),commonCode.getName());
            codes.add(code);
        }

        return codes;
    }

    @Override
    public List<CommonCode> getDcuGroupResponseDto() {

        List<com.sara.project.ddc.CommonCode> commonCodes = ddcRepository.getGroupDcu();
        List<CommonCode> codes = new ArrayList<>();
        for (com.sara.project.ddc.CommonCode commonCode :  commonCodes){
            CommonCode code = new CommonCode(commonCode.getId(),commonCode.getName());
            codes.add(code);
        }

        return codes;
    }


    @Override
    public List<CommonGroupCode> getDdcGroupResponseDtoCust() {


        List<CommonGroupCode>codes =  ddcRepository.findByDcuIsNull()
                .parallelStream()
                .map(ddc -> new CommonGroupCode(ddc.getDdc().toString(), ddc.getDdc().toString(), ddc.getGateway().getIpAddress()))
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
        return codes;
    }

    @Override
    public List<CommonGroupCode> getDcuGroupResponseDtoCust() {


        List<CommonGroupCode>codes =  ddcRepository.findByDcuIsNotNull()
                .parallelStream()
                .map(dcu -> new CommonGroupCode(dcu.getDdc().toString()+"-"+(dcu.getDcu()-699), dcu.getDdc().toString()+"-"+(dcu.getDcu()-699), dcu.getGateway().getIpAddress()))
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
        return codes;
    }

    @Override
    public List<CommonCode> getDiResponseDto(Integer id) {
        List<Ddc> ddcs = ddcRepository.findByDdc(id);
        List<CommonCode> codes = new ArrayList<>();
        for ( Ddc ddc :  ddcs){
            CommonCode code = new CommonCode(Integer.parseInt(ddc.getId().toString()),"DI:"+ddc.getDi().toString());
            codes.add(code);
        }

        return codes;

    }
}
