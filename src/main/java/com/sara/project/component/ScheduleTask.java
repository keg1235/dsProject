package com.sara.project.component;

import com.sara.project.datacall.DataCallService;
import com.sara.project.ddc.Ddc;
import com.sara.project.ddc.DdcRepository;
import com.sara.project.ddc.dto.DdcResponseDto;
import com.sara.project.eletron.Eletron;
import com.sara.project.eletron.EletronService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ScheduleTask {
    @Autowired
    private DataCallService dataCallService;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private DdcRepository ddcRepository;

    @Autowired
    private EletronService eletronService;

    public static Integer time = 0;


    @Scheduled(fixedRate = 1000)
    public void task1(){
        dataCallService.datacall();
    }

    @Scheduled(fixedRate = 1000)
    public void task2(){

            List<Ddc> dcpList = ddcRepository.findAll();
            List<DdcResponseDto> dcpResponseDtoList
                    = dcpList.stream().map(DdcResponseDto::of)
                    .collect(Collectors.toList());

        this.template.convertAndSend("/topic/info", dcpResponseDtoList);


    }
    /*
    @Scheduled(fixedRate = 1000)
    public void task2(){

       dataCallService.datacall();
            //System.out.println("scheduled");

    }*/

    // 15분 마다 한번씩
    //

//
//    //@Scheduled(fixedRate = 10000)
//    @Scheduled(cron = "0 0/2 * * * *")
//    public void task2(){
//
//        List<DdcResponseDto> datacall =null ;
//        dataCallService.stateChek();
//        //this.template.convertAndSend("/topic/fiveState",  eletronService.getEletronResponseDtoList());
//
//
//    }


}
