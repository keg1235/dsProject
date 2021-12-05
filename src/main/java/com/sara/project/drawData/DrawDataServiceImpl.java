package com.sara.project.drawData;

import com.sara.project.dcp.Dcp;
import com.sara.project.dcp.DcpRepository;
import com.sara.project.dcp.dto.DcpResponseDto;
import com.sara.project.dcpSet.DcpSet;
import com.sara.project.dcpSet.DcpSetRepository;
import com.sara.project.ddc.Ddc;
import com.sara.project.ddc.DdcRepository;
import com.sara.project.drawData.dto.*;
import com.sara.project.gateway.GatewayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class DrawDataServiceImpl implements DrawDataService{

    @Autowired
    private DrawDataRepository drawDataRepository;

    @Autowired
    private ShadowRepository shadowRepository;

    @Autowired
    private DdcRepository ddcRepository;

    @Autowired
    private DcpSetRepository dcpSetRepository;

    @Autowired
    private DrawGroupRepository drawGroupRepository;

    @Override
    public DrawAllResponseDto getDrawList(Long id,Boolean type) {
        List<DrawData> drawDatas = drawDataRepository.findByGroupIdAndGroupType(id,"Objects");
 //       productCategoryList.stream().map(
//                product -> ProductResponseDto.of(product, connect.getIp(), connect.getPort()))
//                .collect(Collectors.toList()
        try {
            //log.info("{}",drawDatas);
            List<DrawDataResponseDto> drawDataResponseDtos
                    = drawDatas.stream().map(drawData -> DrawDataResponseDto.of(drawData, dICheck(drawData) ,doCheck(drawData) ,dcpCheck(drawData),type))
                    .collect(Collectors.toList());
            List<DrawData> backDraw = drawDataRepository.findByGroupIdAndGroupType(id,"Background");
            DrawDataResponseDto background  = DrawDataResponseDto.of(backDraw.get(0));

            DrawAllResponseDto drawAllResponseDto =  DrawAllResponseDto.of(background,drawDataResponseDtos,id);

            return drawAllResponseDto;
        }catch (Exception e){
            log.info("{}",e);
            List<DrawDataResponseDto> drawDataResponseDtos
                    = drawDatas.stream().map(drawData -> DrawDataResponseDto.of(drawData, dICheck(drawData) ,doCheck(drawData) ,dcpCheck(drawData),type))
                    .collect(Collectors.toList());
            DrawDataResponseDto background  = new DrawDataResponseDto();
            DrawAllResponseDto drawAllResponseDto =  DrawAllResponseDto.of(background,drawDataResponseDtos,id);
            return drawAllResponseDto;
        }



    }
    public Ddc dICheck(DrawData drawData){
        Ddc ddc = null;
        if (drawData.getDidoType() != null){
            ddc = drawData.getDidoType().equals("DI") ? ddcRepository.getOne(drawData.getTypeId()) : null;
        }


        return ddc;
    }

    public Ddc doCheck(DrawData drawData){
        Ddc ddc = null;
        if (drawData.getDidoType() != null){
            ddc = drawData.getDidoType().equals("DO") ? ddcRepository.getOne(drawData.getTypeId()) : null;
        }


        return ddc;
    }

    public DcpSet dcpCheck(DrawData drawData){
        DcpSet dcpSet = null;
        if (drawData.getDidoType() != null){
            dcpSet = drawData.getDidoType().equals("DCP") ? dcpSetRepository.getOne(drawData.getTypeId()) : null;
        }


        return dcpSet;
    }

    @Override
    public List<DrawDataResponseDto> getDrawAll() {
        List<DrawData> drawDatas = drawDataRepository.findAll();
        List<DrawDataResponseDto> drawDataResponseDtos
                = drawDatas.stream().map(DrawDataResponseDto::of)
                .collect(Collectors.toList());
        return drawDataResponseDtos;
    }

    @Override
    public List<DrawGroupResponseDto> getDrawGroupAll() {
        List<DrawGroup> drawGroups = drawGroupRepository.findAll();
        List<DrawGroupResponseDto> drawGroupResponseDtos
                = drawGroups.stream().map(DrawGroupResponseDto::of)
                .collect(Collectors.toList());
        return drawGroupResponseDtos;
    }




    @Transactional
    @Override
    public void save(DrawAllRequestDto drawAllRequestDto) {

        shadowRepository.deleteByGroupId(drawAllRequestDto.getGroupId());
        drawDataRepository.deleteByGroupId(drawAllRequestDto.getGroupId());
        DrawDataRequestDto backgroundImage = drawAllRequestDto.getBackgroundImage();
        if (backgroundImage !=  null){
            DrawData background = backgroundImage.of(drawDataRepository.findMaxValue());
            background.setGroupType("Background");
            background.setShadow(shadowSave(backgroundImage.getShadow(),drawAllRequestDto.getGroupId()));
            background.setGroupId(drawAllRequestDto.getGroupId());
            drawDataRepository.save(background);
        }
        for(DrawDataRequestDto drawDataRequestDto : drawAllRequestDto.getObjects()){
            DrawData drawData  = drawDataRequestDto.of(drawDataRepository.findMaxValue());
            drawData.setShadow(shadowSave(drawDataRequestDto.getShadow(),drawAllRequestDto.getGroupId()));
            drawData.setGroupType("Objects");
            drawData.setGroupId(drawAllRequestDto.getGroupId());
            drawDataRepository.save(drawData);
        }

    }


    @Override
    public void dataGroupSave(DrawGroupRequestDto drawGroupRequestDto) {
        Long id = drawGroupRepository.findMaxValue();
        DrawGroup drawGroup = drawGroupRequestDto.of(id);
        drawGroupRepository.save(drawGroup);

    }

    @Transactional
    @Override
    public void dataGroupDelete(Long id) {
        shadowRepository.deleteByGroupId(id);
        drawDataRepository.deleteByGroupId(id);
        drawGroupRepository.deleteById(id);
    }

    @Override
    public void dataGroupUpdate(DrawGroupRequestDto drawGroupRequestDto) {

        DrawGroup drawGroup = drawGroupRequestDto.of();
        drawGroupRepository.save(drawGroup);
    }

    @Override
    public DrawGroupResponseDto getDrawGroup(Long id) {
        DrawGroup drawGroup = drawGroupRepository.getOne(id);
        return DrawGroupResponseDto.of(drawGroup);
    }

    public Shadow shadowSave (Shadow shadow,Long id) {
        if (shadow != null){
            shadow.setId(shadowRepository.findMaxValue());
            shadow.setGroupId(id);
            shadowRepository.save(shadow);
            return shadow;
        }else{
            return null;
        }

    }

    @Transactional
    @Override
    public void delete(Long id) {
        shadowRepository.deleteByGroupId(id);
        drawDataRepository.deleteByGroupId(id);

    }
}
