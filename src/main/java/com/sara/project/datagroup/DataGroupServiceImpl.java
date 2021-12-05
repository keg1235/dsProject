package com.sara.project.datagroup;

import com.sara.project.datagroup.dto.DataGroupCard;
import com.sara.project.datagroup.dto.DataGroupMapper;
import com.sara.project.datagroup.dto.DataGroupRequestDto;
import com.sara.project.datagroup.dto.DataGroupResponseDto;
import com.sara.project.dcp.Dcp;
import com.sara.project.dcp.DcpRepository;
import com.sara.project.ddc.Ddc;
import com.sara.project.ddc.DdcRepository;
import com.sara.project.ddcmessage.DdcMessage;
import com.sara.project.ddcmessage.ddcMessageDto.DdcMessageRequestDto;
import com.sara.project.eletron.Eletron;
import com.sara.project.eletron.dto.EletronResponseDto;
import com.sara.project.scdata.ScData;
import com.sara.project.scdata.ScDataRepository;
import com.sara.project.scdata.dto.ScDataResponseDto;
import com.sara.project.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DataGroupServiceImpl implements DataGroupService{

    @Autowired
    private DataGroupRepository dataGroupRepository;

    @Autowired
    private DdcRepository ddcRepository;

    @Autowired
    private DcpRepository dcpRepository;

    @Autowired
    private ScDataRepository scDataRepository;


    @Transactional
    @Override
    public void listsave(List<DataGroupRequestDto> dataGroupRequestDtos) {
        dataGroupRepository.deleteByGroupId(dataGroupRequestDtos.get(0).getGroupId());
        Long Groupid = dataGroupRepository.findMaxGroupValue() + 1;

        ScData scData = new ScData();
        if (dataGroupRequestDtos.get(0).getScdataid() == null){
            scData = null;
        }else{
            scData = scDataRepository.findById(dataGroupRequestDtos.get(0).getScdataid()).orElse(null);
        }
        Dcp dcp = new Dcp();
        if (dataGroupRequestDtos.get(0).getDcpid() == null){
            dcp = null;
        }else{
            dcp = dcpRepository.findById(dataGroupRequestDtos.get(0).getDcpid()).orElse(null);
        }
        for (DataGroupRequestDto dataGroupRequestDto :  dataGroupRequestDtos){

            Long generatedId = dataGroupRepository.findMaxValue() + 1;
            Optional<Ddc> ddc= ddcRepository.findById(dataGroupRequestDto.getDdcId());
            DataGroup dataGroup = dataGroupRequestDto.of(generatedId,Groupid,ddc.get(),dcp,scData);
            dataGroupRepository.save(dataGroup);
        }
    }

    @Transactional
    @Override
    public void delete(long id) {
        dataGroupRepository.deleteByGroupId(id);
    }

    @Override
    public List<DataGroupResponseDto> getDataGroupList(Long id) {
        List<DataGroupResponseDto> dataGroupResponseDtos = dataGroupRepository.findByGroupId(id);
        return dataGroupResponseDtos;
    }

    @Override
    public List<DataGroupMapper> getDataGroupLists() {

        List<DataGroupMapper> dataGroupMappers = dataGroupRepository.findgroup();

        return dataGroupMappers;
    }

    @Override
    public List<DataGroupCard> getDataGroupCard() {
        List<DataGroupMapper> dataGroupMappers = dataGroupRepository.findgroup();
       List<DataGroupCard> dataGroupCards = new ArrayList<>();
        for (DataGroupMapper dataGroupMapper :dataGroupMappers){

            ScData scData =new ScData();
            Dcp dcp =new Dcp();
            if(dataGroupMapper.getScdataid() != null){
                scData =  scDataRepository.findById(dataGroupMapper.getScdataid()).orElse(null);
            }
            if(dataGroupMapper.getDcpid() != null){
                dcp = dcpRepository.findById(dataGroupMapper.getDcpid()).orElse(null);
            }
            dataGroupCards.add(DataGroupCard.of(dataGroupMapper,scData,dcp));
        }


    return dataGroupCards;
    }
}
