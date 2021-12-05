package com.sara.project.dcp;

import com.sara.project.dcp.dto.DcpRequestDto;
import com.sara.project.dcp.dto.DcpResponseDto;
import com.sara.project.dcp.Dcp;
import com.sara.project.dcp.DcpRepository;
import com.sara.project.dcp.dto.DcpResponseDto;
import com.sara.project.ddc.Ddc;
import com.sara.project.ddc.DdcRepository;
import com.sara.project.gateway.GatewayRepository;
import com.sara.project.gateway.TbGateway;
import com.sara.project.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DcpServiceImpl implements DcpService {

    @Autowired
    private DcpRepository dcpRepository;

    @Autowired
    private GatewayRepository gatewayRepository;

    @Override
    public List<DcpResponseDto> getDcpResponseDtoList() {
        List<Dcp> dcpList = dcpRepository.findAll();
        List<DcpResponseDto> dcpResponseDtoList
                = dcpList.stream().map(DcpResponseDto::of)
                .collect(Collectors.toList());
        return dcpResponseDtoList;
    }

    @Override
    public DcpResponseDto getDcpResponseDto(Long id) {
        Optional<Dcp> dcp = dcpRepository.findById(id);
        DcpResponseDto dcpResponseDto =
                DcpResponseDto.of(dcp.get());

        return dcpResponseDto;
    }



    @Override
    public Page<DcpResponseDto> getDcpPage(Pageable pageRequest) {
        Page<Dcp> dcps = dcpRepository.findAll(pageRequest);

        Page<DcpResponseDto> dcpResponseDtopage = dcps.map(
                dcp -> DcpResponseDto.of(dcp)
        );
        return dcpResponseDtopage;
    }

    @Transactional
    @Override
    public void save(DcpRequestDto dcpRequestDto) {
        try {
            Long generatedId = dcpRepository.findMaxValue() + 1;
            TbGateway tbGateway = gatewayRepository.findById(dcpRequestDto.getGateway_id()).orElse(null);
            Dcp dcp = dcpRequestDto.of(generatedId,tbGateway);
            if(dcpRequestDto.getMainYn().equals("YES")){
                List<Dcp> dcps = dcpRepository.findAll();
                for(Dcp dcpsub :dcps ){
                    dcpsub.setMainYn("NO");
                    dcpRepository.save(dcpsub);
                }

            }
            dcpRepository.save(dcp);


        } catch (Exception e) {
            throw new ServiceException("DcpService", "save", e.toString());
        }

    }

    @Transactional
    @Override
    public void update(Long id, DcpRequestDto dcpRequestDto) {
        try {
            TbGateway tbGateway = gatewayRepository.findById(dcpRequestDto.getGateway_id()).orElse(null);
            Dcp dcp = dcpRequestDto.of(id,tbGateway);
            if(dcpRequestDto.getMainYn().equals("YES")){
                List<Dcp> dcps = dcpRepository.findAll();
                for(Dcp dcpsub :dcps ){
                    dcpsub.setMainYn("NO");
                    dcpRepository.save(dcpsub);
                }

            }
            dcpRepository.save(dcp);
        } catch (Exception e) {
            throw new ServiceException("DcpService", "update", e.toString());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            dcpRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("DcpService", "delete", e.toString());
        }
    }
}
