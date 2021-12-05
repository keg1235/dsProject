package com.sara.project.datacall;

import com.sara.project.EchoClient;
import com.sara.project.EchoClientHandler;
import com.sara.project.datagroup.DataGroup;
import com.sara.project.datagroup.DataGroupRepository;
import com.sara.project.datagroup.dto.DataGroupMapper;
import com.sara.project.dcp.Dcp;
import com.sara.project.dcp.DcpRepository;
import com.sara.project.dcp.DcpService;
import com.sara.project.dcpReport.DcpReport;
import com.sara.project.dcpReport.DcpReportRepository;
import com.sara.project.dcpReport.dto.DcpReportRequestDto;
import com.sara.project.dcpSet.DcpSet;
import com.sara.project.dcpSet.DcpSetRepository;
import com.sara.project.dcpSet.DcpSetService;
import com.sara.project.dcpSet.dto.DcpGroupDto;
import com.sara.project.ddc.Ddc;
import com.sara.project.ddc.DdcRepository;

import com.sara.project.ddc.dto.DdcGroupMapper;
import com.sara.project.ddc.dto.DdcMapper;
import com.sara.project.ddc.dto.DdcResponseDto;
import com.sara.project.ddcmessage.DdcMessageRepository;
import com.sara.project.ddcmessage.ddcMessageDto.DdcUser;
import com.sara.project.eletron.Eletron;
import com.sara.project.eletron.EletronRepository;
import com.sara.project.eletron.EletronService;
import com.sara.project.eletron.dto.EletronRequestDto;
import com.sara.project.gateway.GatewayRepository;
import com.sara.project.gateway.TbGateway;
import com.sara.project.pageSet.PageSet;
import com.sara.project.pageSet.PageSetRepository;
import com.sara.project.scdata.ScData;
import com.sara.project.scdata.ScDataRepository;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class DataCallServiceImpl implements DataCallService {

    @Autowired
    private DdcRepository ddcRepository;

    @Autowired
    private GatewayRepository gatewayRepository;

    public static boolean connect = true;

    public static Integer connectCount = 0;

    @Autowired
    private DdcMessageRepository ddcMessageRepository;

    @Autowired
    private DcpRepository dcpRepository;

    @Autowired
    private EletronService eletronService;

    @Autowired
    private EletronRepository eletronRepository;

    @Autowired
    private DataGroupRepository dataGroupRepository;

    public static boolean saving = false;

    public static Integer waitTime = 10;

    @Autowired
    private ScDataRepository scDataRepository;

    @Autowired
    private PageSetRepository pageSetRepository;

    @Autowired
    private DcpSetRepository dcpSetRepository;

    @Autowired
    private DcpSetService dcpSetService;

    @Autowired
    private DcpReportRepository dcpReportRepository;

    public void dcpScan(){
        List<DcpSet> dcpSets = dcpSetRepository.findAll();
        String result = "";
        String Value = "";

        for (DcpSet dcpSet : dcpSets) {
            if (dcpSet.getReadWriteType().equals("DCP")) {
                ByteBuf buf = Unpooled.buffer();
                EchoClientHandler echoClientHandler = new EchoClientHandler();
                buf.writeByte(0x00);
                buf.writeByte(0x03); //TransFor id?
                buf.writeByte(0x00);
                buf.writeByte(0x00);
                buf.writeByte(0x00);
                buf.writeByte(0x06); // 길이?
                buf.writeByte(dcpSet.getDcp()); // 기기 번호 DCP or DDC
                buf.writeByte(0x03); //function code readCoil , read Holding register
                if (dcpSet.getAddress() < 256) {
                    buf.writeByte(0x00);
                    buf.writeByte(dcpSet.getAddress()); //메모리 주소
                } else {
                    String cust = Integer.toBinaryString(dcpSet.getAddress());


                    String cust2 = cust.substring(cust.length() - 8, cust.length());
                    // log.info(cust2);
                    // log.info(cust.substring(0, cust.length()-8));
                    buf.writeByte(Integer.parseInt(cust.substring(0, cust.length() - 8), 2));
                    buf.writeByte(Integer.parseInt(cust2, 2));

                }
                buf.writeByte(0x00);
                buf.writeByte(dcpSet.getIndexNum()); //데이터 공간
                echoClientHandler.setDcpType("");
                echoClientHandler.setDcpSetBufferResult(null);
                EchoClient echoClient = new EchoClient(dcpSet.getGateway().getIpAddress(), dcpSet.getGateway().getPort());

                long accum = 0;

                double resultdouble;
                try {
                    echoClient.getDataReq(buf, dcpSet, dcpSetRepository); //바이트버퍼에 넣고
                    Thread.sleep(100);
                    int sleepCnt = 0;
                    ByteBuf response = null;

                    while (true) {
                        sleepCnt++;
                        response = new EchoClientHandler().getDcpSetBufferResult();
                        //  log.info("{}", response);
                        if (response != null) {
                            int size = response.readableBytes();
                            byte[] test = new byte[size - 9];
                            // for문을 돌며 가져온 바이트 값을 연결합니다.
                            int f = 0;
                            for (int i = 9; i < size; i++) {
                                test[f] = response.getByte(i);
                                f++;
                            }

                            if (dcpSet.getDataType().equals("Long")) {
                                result = Long.valueOf(ByteBuffer.wrap(test).getLong()).toString();
                                Value = Long.toString(((Long.valueOf(result) - Long.valueOf(dcpSet.getVolumeMin())) / (Long.valueOf(dcpSet.getVolumeMax()) - Long.valueOf(dcpSet.getVolumeMin()))) * (Long.valueOf(dcpSet.getDisplayMax()) - Long.valueOf(dcpSet.getDisplayMin())) + Long.valueOf(dcpSet.getDisplayMin()));
                            } else if (dcpSet.getDataType().equals("Word")) {
                                result = Integer.toString(response.getByte(size - 1));
                                // log.info("{}",((Integer.valueOf(result) - Integer.valueOf(dcpSet.getVolumeMin())) / (Integer.valueOf(dcpSet.getVolumeMax()) - Integer.valueOf(dcpSet.getVolumeMin()))));
                                Value = Double.toString(((Integer.valueOf(result) * 1.0 - Integer.valueOf(dcpSet.getVolumeMin())) / (Integer.valueOf(dcpSet.getVolumeMax()) - Integer.valueOf(dcpSet.getVolumeMin()))) * (Integer.valueOf(dcpSet.getDisplayMax()) - Integer.valueOf(dcpSet.getDisplayMin())) + Integer.valueOf(dcpSet.getDisplayMin()));
                            } else if (dcpSet.getDataType().equals("Double")) {
                                result = BigDecimal.valueOf(ByteBuffer.wrap(test).getDouble()).toString();
                                Value = Long.toString(((Long.valueOf(result) - Long.valueOf(dcpSet.getVolumeMin())) / (Long.valueOf(dcpSet.getVolumeMax()) - Long.valueOf(dcpSet.getVolumeMin()))) * (Long.valueOf(dcpSet.getDisplayMax()) - Long.valueOf(dcpSet.getDisplayMin())) + Long.valueOf(dcpSet.getDisplayMin()));
                            }
                            // log.info(Value);
                            if (dcpSet.getValue() == null || !dcpSet.getValue().equals(Value + dcpSet.getUnit())) {
                                dcpSet.setValue(Value + dcpSet.getUnit());
                                DcpReport dcpReport = new DcpReportRequestDto().of(dcpReportRepository.findMaxValue() + 1, dcpSet);
                                dcpReportRepository.save(dcpReport);
                            }
                            dcpSet.setValue(Value + dcpSet.getUnit());
                            dcpSetRepository.save(dcpSet);
                            // 읽을 수 있는 바이트의 길이만큼 바이트 배열을 초기화합니다.


                            echoClientHandler.channelReadComplete();
                            break;
                        }
                        if (sleepCnt > waitTime*10) {// 통신 안됨gjdmsg
                            if (!connect) {
                                connect = true;
                                break;
                            }

                            break;
                        }else{
                            dcpSet.setValue("연결안됨");
                            dcpSetRepository.save(dcpSet);
                        }
                        Thread.sleep(100);
                    }


                    echoClientHandler.channelReadComplete();
                } catch (Exception e) {
                    try {
                        //System.out.println(e.toString());
                        echoClientHandler.channelReadComplete();
                    } catch (Exception exception) {
                        //System.out.println(exception.toString());
                        //exception.printStackTrace();
                    }
                }


            } else if (dcpSet.getType().equals("DDC")) {

            } else if (dcpSet.getType().equals("DCU")) {

            } else if (dcpSet.getType().equals("DCU")) {

            }

        }

    }

    public void discan( List<DdcGroupMapper> ddcList, ByteBuf buf){
        for (DdcGroupMapper ddc : ddcList) { // ddc id 로 돈다.

            TbGateway tbGateway = gatewayRepository.findByIpAddress(ddc.getIpAddress());
            EchoClientHandler echoClientHandler = new EchoClientHandler();
            buf.writeByte(0x00);
            buf.writeByte(0x02); //명령어 single tan?
            buf.writeByte(0x00);
            buf.writeByte(0x00);
            buf.writeByte(0x00);
            buf.writeByte(0x06);
            buf.writeByte(ddc.getDdc()); // ddc 입력
            buf.writeByte(0x02); //?
            buf.writeByte(0x00);
            buf.writeByte(0x00); //명령어
            buf.writeByte(0x00);
            buf.writeByte(0x01); //데이터 공간
            echoClientHandler.setResultStatus(null);
            EchoClient echoClient = new EchoClient(ddc.getIpAddress(),ddc.getPort());


            try {
                echoClient.getDataReq(buf,ddc.getDdc(),ddcRepository); //바이트버퍼에 넣고
                Thread.sleep(1000);
                int sleepCnt = 0;
                ByteBuf response = null;
                while (true) {
                    sleepCnt++;
                    response = new EchoClientHandler().getResultStatus();
                    //log.info("{}", response);
                    if (response != null) {
                        int size = response.readableBytes();
                        //log.info("{}", size);
                        // 읽을 수 있는 바이트의 길이만큼 바이트 배열을 초기화합니다.
                        String[] message = new String[size];
                        // for문을 돌며 가져온 바이트 값을 연결합니다.
                        for (int i = 0; i < size; i++) {
                            message[i] = String.format("%02x", response.getByte(i) & 0xff);
                            //int num3 = Integer.parseInt(num2,16); // ddc 번호 체크할때 나중에 사용

                        }
                        ////log.info("{}", Integer.parseInt(Integer.toHexString(Integer.parseInt(message[size-1])), 16));
                        ////System.out.println("status : " +  Integer.parseInt(Integer.toHexString(Integer.parseInt(message[size-1])), 16));

                        String status = String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(message[size - 1], 16))));
                        //log.info(status);
                        int count = 1;
                        for (int i = status.length(); i > 0; i--) {
                            Ddc ddc1 = ddcRepository.findByDdcAndDiAndDcuAndGateway_Id(ddc.getDdc(),count,null,tbGateway.getId());


                            //log.info("into" + i);
                            //// log.info("{}", ddc1.getDiStatus());
                            //log.info("{}", Integer.parseInt(String.valueOf(status.charAt(i - 1))));
                            if (ddc1 == null){

                            }else{
                                if (ddc1.getDiStatus() == null || ddc1.getDiStatus() != Integer.parseInt(String.valueOf(status.charAt(i - 1)))) {
                                    //log.info("save");
                                    ddc1.setDiStatus(Integer.parseInt(String.valueOf(status.charAt(i - 1))));
                                    ddcSave(ddc1);
                                    if (ddc1.getUse().equals("YES")) {
                                        sendMessage(ddc1);
                                    }
                                }
                            }

                            //log.info("char{}", status.charAt(i - 1));
                            count++;
                        }

                        break;
                    }

                    if (sleepCnt > waitTime*10) {// 통신 안됨
                        //System.out.println("DI NO Connected");
                        if(!connect){
                            connect =true;
                            break;
                        }else{
                            for (int i = 8 ; i > 0; i--) {
                                Ddc ddc1 = ddcRepository.findByDdcAndDiAndDcuAndGateway_Id(ddc.getDdc(),i,null,tbGateway.getId());
                                if (ddc1 != null) {
                                    if (ddc1.getDiStatus() != null) {
                                        //log.info("save");
                                        ddc1.setDiStatus(null);
                                        ddcSave(ddc1);
                                    }
                                    if (ddc1.getUse().equals("YES")) {
                                        sendMessage(ddc1);
                                    }
                                }
                            }
                            break;
                        }

                    }
                    Thread.sleep(100);

                }
                echoClientHandler.channelReadComplete();
            } catch (Exception e) {
                try {
                    echoClientHandler.channelReadComplete();
                } catch (Exception exception) {

                }
            }

        }
    }

    public void fiveminutescan( List<DataGroupMapper> ddcList, ByteBuf buf){ // 5분 계측기

        Dcp dcp  = dcpRepository.findByMainYn("YES");
        if (dcp != null) {
            Integer status =null;
            TbGateway tbGateway = gatewayRepository.findById(dcp.getGateway().getId()).orElse(null);
            EchoClientHandler echoClientHandler = new EchoClientHandler();
            buf.writeByte(0x00);
            buf.writeByte(0x03); //명령어 single tan?
            buf.writeByte(0x00);
            buf.writeByte(0x00);
            buf.writeByte(0x00);
            buf.writeByte(0x06);
            buf.writeByte(dcp.getDdc()); // ddc 입력
            buf.writeByte(0x03); //?
            buf.writeByte(0x00);
            buf.writeByte(0x0f); //명령어130
            //buf.writeByte(0xa2); //명령어162
            buf.writeByte(0x00);
            buf.writeByte(0x01); //데이터 공간
            echoClientHandler.setResultfive(null);
            EchoClient echoClient = new EchoClient(tbGateway.getIpAddress(), tbGateway.getPort());


            try {
                echoClient.getDataReq(buf, dcp.getDdc(),ddcRepository); //바이트버퍼에 넣고
                Thread.sleep(1000);
                int sleepCnt = 0;
                ByteBuf response = null;
                while (true) {
                    sleepCnt++;
                    response = new EchoClientHandler().getResultfive();
                    //log.info("{}", response);
                    if (response != null) {
                        byte[] test = new byte[2];
                        int size = response.readableBytes();
                        int f = 0;
                        for (int i = 9; i < size; i++) {
                            test[f] = response.getByte(i);
                            f++;
                        }

                        StringBuffer sb = new StringBuffer(test.length * 2);
                        String hexaDecimal ="";

                        for(int x = 0; x < test.length; x++)
                        {
                            hexaDecimal = "0" + Integer.toHexString(0xff & test[x]);
                            sb.append(hexaDecimal.substring(hexaDecimal.length()-2));
                        }

                        status = Integer.parseInt(sb.toString(), 16);

                        //log.info(Integer.toString(status));

                        EletronRequestDto eletronRequestDto = new EletronRequestDto();
                        eletronRequestDto.setDcp(dcp.getId());
                        eletronRequestDto.setIpAddress(tbGateway.getIpAddress());
                        eletronRequestDto.setInType(dcp.getType());
                        eletronRequestDto.setInValue(status + dcp.getSaveValue());
                        eletronService.save(eletronRequestDto);
                        echoClientHandler.channelReadComplete();

                        break;
                    }

                    if (sleepCnt > waitTime*10) {// 통신 안됨gjdmsg
                        if(!connect){
                            connect =true;
                            break;
                        }
                        echoClientHandler.channelReadComplete();

                        break;
                    }
                    Thread.sleep(100);

                }

            } catch (Exception e) {
                //System.out.println(e.toString());
                try {
                    echoClientHandler.channelReadComplete();
                } catch (Exception exception) {

                }
            }

            for (DataGroupMapper ddc : ddcList) { // ddc id 로 돈다.
                if (ddc.getDcpid() != null) {
                    int setOnoff = 2;
                    Dcp dcp2 = dcpRepository.findById(ddc.getDcpid()).orElse(null);
                    if (dcp2 != null) {
                        if (dcp2.getUseYn().equals("YES")) {

                            if (status != null) {
                                if (dcp2.getOffType().equals("big")) {
                                    if (dcp2.getOffSuggest() <= status) {
                                        setOnoff = 0;
                                    }
                                } else if (dcp2.getOffType().equals("small")) {
                                    if (dcp2.getOffSuggest() >= status) {
                                        setOnoff = 0;
                                    }
                                }

                                if (dcp2.getOnType().equals("big")) {
                                    if (dcp2.getOnSuggest() <= status) { // 1> 0
                                        setOnoff = 1;
                                    }
                                } else if (dcp2.getOnType().equals("small")) {
                                    if (dcp2.getOnSuggest() >= status) {
                                        setOnoff = 1;
                                    }
                                }

                                if (setOnoff < 2) {
                                    List<DataGroup> dataGroups = dataGroupRepository.findByDcpId(dcp2.getId());
                                    ArrayList<DataCallRequest> arrayList = new ArrayList();
                                    for (DataGroup dataGroup : dataGroups) {
                                        DataCallRequest dataCallRequest = new DataCallRequest();
                                        Ddc ddc1 = ddcRepository.findById(dataGroup.getDdcId().getId()).orElse(null);
                                        if (ddc1 != null) {
                                            dataCallRequest.setId(ddc1.getId());
                                            dataCallRequest.setOnoffstatus(setOnoff);
                                            dataCallRequest.setDdc(ddc1.getDdc());
                                            dataCallRequest.setDcu(ddc1.getDcu());
                                            dataCallRequest.setDoId(ddc1.getDoId());
                                            arrayList.add(dataCallRequest);
                                        }
                                    }
                                    if (arrayList.size() > 0) {
                                        dataOnOffMultiCust3(arrayList, dcp2.getDelayTimer());
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

    }
    public void dcpMonitor( List<DataGroupMapper> ddcList, ByteBuf buf) { // 총계수s

        List<DcpGroupDto> dcpGroupDtos =  dcpSetService.getDcpGroupDto();
        for (DcpGroupDto dcp : dcpGroupDtos) { // ddc id 로 돈다.
            if (dcp.getType().equals("coil") ){

            }
            TbGateway tbGateway = gatewayRepository.findByIpAddress(dcp.getGateWayAddr());
            EchoClientHandler echoClientHandler = new EchoClientHandler();
            buf.writeByte(0x00);
            buf.writeByte(0x02); //명령어 single tan?
            buf.writeByte(0x00);
            buf.writeByte(0x00);
            buf.writeByte(0x00);
            buf.writeByte(0x06);
            buf.writeByte(dcp.getId()); // ddc 입력
            buf.writeByte(0x02); //?
            buf.writeByte(0x00);
            buf.writeByte(0x00); //명령어
            buf.writeByte(0x00);
            buf.writeByte(0x01); //데이터 공간
            echoClientHandler.setResultStatus(null);
            EchoClient echoClient = new EchoClient(dcp.getGateWayAddr(),dcp.getPortAddr());


            try {
                echoClient.getDataReq(buf,dcp.getId(),ddcRepository); //바이트버퍼에 넣고
                Thread.sleep(1000);
                int sleepCnt = 0;
                ByteBuf response = null;
                while (true) {
                    sleepCnt++;
                    response = new EchoClientHandler().getResultStatus();
                    log.info("{}", response);
                    if (response != null) {
                        int size = response.readableBytes();
                        log.info("{}", size);
                        // 읽을 수 있는 바이트의 길이만큼 바이트 배열을 초기화합니다.
                        String[] message = new String[size];
                        // for문을 돌며 가져온 바이트 값을 연결합니다.
                        for (int i = 0; i < size; i++) {
                            message[i] = String.format("%02x", response.getByte(i) & 0xff);
                            //int num3 = Integer.parseInt(num2,16); // ddc 번호 체크할때 나중에 사용

                        }
                        //log.info("{}", Integer.parseInt(Integer.toHexString(Integer.parseInt(message[size-1])), 16));
                        //System.out.println("status : " +  Integer.parseInt(Integer.toHexString(Integer.parseInt(message[size-1])), 16));

                        String status = String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(message[size - 1], 16))));
                        log.info(status);
                        int count = 1;
                        for (int i = status.length(); i > 0; i--) {
                            Ddc ddc1 = ddcRepository.findByDdcAndDiAndDcuAndGateway_Id(dcp.getId(),count,null,tbGateway.getId());


                            log.info("into" + i);
                            // log.info("{}", ddc1.getDiStatus());
                            log.info("{}", Integer.parseInt(String.valueOf(status.charAt(i - 1))));
                            if (ddc1 == null){

                            }else{
                                if (ddc1.getDiStatus() == null || ddc1.getDiStatus() != Integer.parseInt(String.valueOf(status.charAt(i - 1)))) {
                                    log.info("save");
                                    ddc1.setDiStatus(Integer.parseInt(String.valueOf(status.charAt(i - 1))));
                                    ddcSave(ddc1);
                                    if (ddc1.getUse().equals("YES")) {
                                        sendMessage(ddc1);
                                    }
                                }
                            }

                            log.info("char{}", status.charAt(i - 1));
                            count++;
                        }

                        break;
                    }

                    if (sleepCnt > waitTime*10) {// 통신 안됨
                        System.out.println("DI NO Connected");
                        if(!connect){
                            connect =true;
                            break;
                        }else{
                            for (int i = 8 ; i > 0; i--) {
                                Ddc ddc1 = ddcRepository.findByDdcAndDiAndDcuAndGateway_Id(dcp.getId(),i,null,tbGateway.getId());
                                if (ddc1 != null) {
                                    if (!ddc1.getDiStatus().equals(null)) {
                                        log.info("save");
                                        ddc1.setDiStatus(null);
                                        ddcSave(ddc1);
                                    }
                                    if (ddc1.getUse().equals("YES")) {
                                        sendMessage(ddc1);
                                    }
                                }
                            }
                            break;
                        }

                    }
                    Thread.sleep(100);

                }
                if (echoClientHandler != null){
                    echoClientHandler.channelReadComplete();
                }

            } catch (Exception e) {
                try {
                    if (echoClientHandler != null){
                        echoClientHandler.channelReadComplete();
                    }

                } catch (Exception exception) {

                }
            }

        }
    }


    public void totalpower( List<DataGroupMapper> ddcList, ByteBuf buf){ // 총계수
        Dcp dcp  = dcpRepository.findByMainYn("YES");

        if (dcp != null) {
            ////System.out.println(dcp.getGateway().getId());
            TbGateway tbGateway = gatewayRepository.findById(dcp.getGateway().getId()).orElse(null);
            EchoClientHandler echoClientHandler = new EchoClientHandler();
            buf.writeByte(0x00);
            buf.writeByte(0x03); //명령어 single tan?
            buf.writeByte(0x00);
            buf.writeByte(0x00);
            buf.writeByte(0x00);
            buf.writeByte(0x06);
            buf.writeByte(dcp.getDdc()); // ddc 입력
            buf.writeByte(0x03); //?
            buf.writeByte(0x00);
            //buf.writeByte(0x82); //명령어130
            buf.writeByte(0xa2); //명령어162
            buf.writeByte(0x00);
            buf.writeByte(0x04); //데이터 공간
            echoClientHandler.setResulttotalResult(null);

            EchoClient echoClient = new EchoClient(tbGateway.getIpAddress(), tbGateway.getPort());
            long accum = 0;
            byte[] test = new byte[8];
            double resultdouble;
            try {
                echoClient.getDataReq(buf, dcp.getDdc(),ddcRepository);


                Thread.sleep(100);
                int sleepCnt = 0;
                ByteBuf response = null;
                while (true) {
                    sleepCnt++;
                    response = new EchoClientHandler().getResulttotalResult();
                    ////  log.info("{}", response);
                    if (response != null) {
                        int size = response.readableBytes();
                        // 읽을 수 있는 바이트의 길이만큼 바이트 배열을 초기화합니다.
                        String[] message = new String[size];

                        // for문을 돌며 가져온 바이트 값을 연결합니다.
                        int f = 0;
                        for (int i = 9; i < size; i++) {
                            test[f] = response.getByte(i);
                            f++;
                        }
                        List<Eletron> eletrons= eletronRepository.findByDcp(dcp.getId());
                        Eletron eletron= eletrons.get(eletrons.size()-1);
                        eletron.setNowTotal(BigDecimal.valueOf(ByteBuffer.wrap(test).getDouble()));
                        eletronRepository.save(eletron);
                        echoClientHandler.channelReadComplete();
                        break;
                    }

                    if (sleepCnt > waitTime*10) {// 통신 안됨gjdmsg
                        if(!connect){
                            connect =true;
                            break;
                        }

                        break;
                    }
                    Thread.sleep(100);


                }
            } catch (Exception e) {
                try {
                    //System.out.println(e.toString());
                    echoClientHandler.channelReadComplete();
                } catch (Exception exception) {
                    //System.out.println(exception.toString());
                    exception.printStackTrace();
                }
            }

        }

    }

    public void  diScanSub1(TbGateway tbGateway , DdcMapper ddc,ByteBuf buf){
        EchoClientHandler echoClientHandler = new EchoClientHandler();
        buf.writeByte(0x00);
        buf.writeByte(0x03);
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x06);
        buf.writeByte(ddc.getDdc()); // ddc 입력
        buf.writeByte(0x03); //명령어 read holding register?
        buf.writeByte(0x02); //700
        buf.writeByte(0xbc); //
        buf.writeByte(0x00);
        buf.writeByte(0x06); //12칸읽어오기
        echoClientHandler.setResultSlaveStatus(null);
        String gateway = ddc.getIpAddress();
        int port = ddc.getPort();
        //log.debug("{}",gateway);
        //log.debug("{}",port);

        EchoClient echoClient = new EchoClient(ddc.getIpAddress(),ddc.getPort());


        try {
            echoClient.getDataReq(buf,ddc.getDdc(),ddcRepository); //바이트버퍼에 넣고
            Thread.sleep(100);
            int sleepCnt = 0;
            ByteBuf response = null;
            while (true) {
                sleepCnt++;
                response = new EchoClientHandler().getResultSlaveStatus();
                //log.info("{}", response);
                if (response != null) {
                    int size = response.readableBytes();
                    int num = 700;
                    int num2 = 10;

                    // for문을 돌며 가져온 바이트 값을 연결합니다.
                    insertdim(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(10) & 0xff), 16)))),ddc,700);
                    insertdim(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(12) & 0xff), 16)))),ddc,701);
                    insertdim(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(14) & 0xff), 16)))),ddc,702);
                    insertdim(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(16) & 0xff), 16)))),ddc,703);
                    insertdim(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(18) & 0xff), 16)))),ddc,704);
                    insertdim(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(20) & 0xff), 16)))),ddc,705);


                    break;
                }
                if (sleepCnt > waitTime*10) {// 통신 안됨
                    if(!connect){
                        connect =true;
                        break;
                    }
                    List<Ddc> ddc1 = ddcRepository.findByDdcAndGateway_IdAndDcuIsNotNull(ddc.getDdc(),tbGateway.getId());
                    for(Ddc ddc2 :  ddc1) {
                        ddc2.setDiStatus(null);
                        ddcSave(ddc2);
                        if (ddc2.getUse().equals("YES")) {
                            sendMessage(ddc2);
                        }
                    }
                    break;
                }
                Thread.sleep(100);

            }
            echoClientHandler.channelReadComplete();
        } catch (Exception e) {
            try {
                echoClientHandler.channelReadComplete();
            } catch (Exception exception) {

            }
        }
    }

    public void  diScanSub2(TbGateway tbGateway , DdcMapper ddc,ByteBuf buf){

        EchoClientHandler echoClientHandler = new EchoClientHandler();
        buf.writeByte(0x00);
        buf.writeByte(0x03);
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x06);
        buf.writeByte(ddc.getDdc()); // ddc 입력
        buf.writeByte(0x03); //명령어 read holding register?
        buf.writeByte(0x02); //700
        buf.writeByte(0xc2); //
        buf.writeByte(0x00);
        buf.writeByte(0x06); //12칸읽어오기
        echoClientHandler.setResultSlaveStatus(null);
        String gateway = ddc.getIpAddress();
        int port = ddc.getPort();
        //log.debug("{}",gateway);
        //log.debug("{}",port);

        EchoClient echoClient = new EchoClient(ddc.getIpAddress(),ddc.getPort());


        try {
            echoClient.getDataReq(buf,ddc.getDdc(),ddcRepository); //바이트버퍼에 넣고
            Thread.sleep(100);
            int sleepCnt = 0;
            ByteBuf response = null;
            while (true) {
                sleepCnt++;
                response = new EchoClientHandler().getResultSlaveStatus();
                //log.info("{}", response);
                if (response != null) {
                    int size = response.readableBytes();
                    int num = 700;
                    int num2 = 10;

                    // for문을 돌며 가져온 바이트 값을 연결합니다.
                    insertdim(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(10) & 0xff), 16)))),ddc,706);
                    insertdim(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(12) & 0xff), 16)))),ddc,707);
                    insertdim(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(14) & 0xff), 16)))),ddc,708);
                    insertdim(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(16) & 0xff), 16)))),ddc,709);
                    insertdim(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(18) & 0xff), 16)))),ddc,710);
                    insertdim(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(20) & 0xff), 16)))),ddc,711);


                    break;
                }
                if (sleepCnt > waitTime*10) {// 통신 안됨
                    if(!connect){
                        connect =true;
                        break;
                    }
                    List<Ddc> ddc1 = ddcRepository.findByDdcAndGateway_IdAndDcuIsNotNull(ddc.getDdc(),tbGateway.getId());
                    for(Ddc ddc2 :  ddc1) {
                        ddc2.setDiStatus(null);
                        ddcSave(ddc2);
                        if (ddc2.getUse().equals("YES")) {
                            sendMessage(ddc2);
                        }
                    }
                    break;
                }
                Thread.sleep(100);

            }
            echoClientHandler.channelReadComplete();
        } catch (Exception e) {
            try {
                echoClientHandler.channelReadComplete();
            } catch (Exception exception) {

            }
        }
    }
    public void dimscan( List<DdcMapper> ddcMList  ,ByteBuf buf){
        for (DdcMapper ddc : ddcMList) { // ddc id 로 돈다.
            //System.out.println(ddc.getDdc());
            TbGateway tbGateway = gatewayRepository.findByIpAddress(ddc.getIpAddress());
            diScanSub1(tbGateway,ddc,buf);
            diScanSub2(tbGateway,ddc,buf);
        }

    }
    public void insertdim (String status, DdcMapper ddc, int num){
        int count = 1;
        //log.info("/************************/"+num);
        //log.info("{}",status.length());

        for (int i = status.length(); i > 0; i--) {
            Ddc ddc1 = ddcRepository.findByDdcAndDiAndDcu(ddc.getDdc(),count,num);
            //log.info("into" + i);
            if (ddc1 != null){
                //log.info("{}", ddc1.getDiStatus());
                //log.info("{}", Integer.parseInt(String.valueOf(status.charAt(i - 1))));
                if (ddc1.getDiStatus() == null || ddc1.getDiStatus() != Integer.parseInt(String.valueOf(status.charAt(i - 1)))) {
                    //log.info("save");
                    ddc1.setDiStatus(Integer.parseInt(String.valueOf(status.charAt(i - 1))));
                    ddcSave(ddc1);
                    if (ddc1.getUse().equals("YES")) {
                        sendMessage(ddc1);
                    }
                }
            }

            //log.info("char{}", status.charAt(i - 1));
            count++;
        }
    }

    public void insertdom (String status, DdcMapper ddc, int num){
        int count = 1;
        //log.info("/************************/"+num);
        //log.info("{}",status.length());

        for (int i = status.length(); i > 0; i--) {
            Ddc ddc1 = ddcRepository.findByDdcAndDoIdAndDcu(ddc.getDdc(),count,num);
            //log.info("into" + i);
            if (ddc1 != null){
                //log.info("{}", ddc1.getDoStatus());
                //log.info("{}", Integer.parseInt(String.valueOf(status.charAt(i - 1))));
                if (ddc1.getDoStatus() == null || ddc1.getDoStatus() != Integer.parseInt(String.valueOf(status.charAt(i - 1)))) {
                    //log.info("save");
                    ddc1.setDoStatus(Integer.parseInt(String.valueOf(status.charAt(i - 1))));
                    ddcSave(ddc1);
                    if (ddc1.getUse().equals("YES")) {
                        sendMessage(ddc1);
                    }
                }
            }

            //log.info("char{}", status.charAt(i - 1));
            count++;
        }
    }

    public void doscan(List<DdcGroupMapper> ddcList  , ByteBuf buf){
        for (DdcGroupMapper ddc : ddcList) { //DO 상태값 저장
            TbGateway tbGateway = gatewayRepository.findByIpAddress(ddc.getIpAddress());
            EchoClientHandler echoClientHandler = new EchoClientHandler();
            buf.writeByte(0x00);
            buf.writeByte(0x01); //명령어 single tan?
            buf.writeByte(0x00);
            buf.writeByte(0x00);
            buf.writeByte(0x00);
            buf.writeByte(0x06);
            buf.writeByte(ddc.getDdc()); // ddc 입력
            buf.writeByte(0x01); //?
            buf.writeByte(0x00);
            buf.writeByte(0x00); //명령어
            buf.writeByte(0x00);
            buf.writeByte(0x08); //데이터 공간
            echoClientHandler.setWriteResultStatus(null);
            EchoClient echoClient = new EchoClient(ddc.getIpAddress(), ddc.getPort());


            try {
                echoClient.getDataReq(buf,ddc.getDdc(),ddcRepository);
                Thread.sleep(100);
                int sleepCnt = 0;
                ByteBuf response = null;
                while (true) {
                    sleepCnt++;
                    response = new EchoClientHandler().getWriteResultStatus();
                    //log.info("{}", response);
                    if (response != null) {
                        int size = response.readableBytes();
                        //log.info("{}", size);
                        // 읽을 수 있는 바이트의 길이만큼 바이트 배열을 초기화합니다.
                        String[] message = new String[size];
                        // for문을 돌며 가져온 바이트 값을 연결합니다.
                        for (int i = 0; i < size; i++) {
                            message[i] = String.format("%02x", response.getByte(i) & 0xff);
                            //int num3 = Integer.parseInt(num2,16); // ddc 번호 체크할때 나중에 사용

                        }
                        ////log.info("{}", Integer.parseInt(Integer.toHexString(Integer.parseInt(message[size-1])), 16));
                        ////System.out.println("status : " +  Integer.parseInt(Integer.toHexString(Integer.parseInt(message[size-1])), 16));

                        String status = String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(message[size - 1], 16))));
                        //log.info("DoONsataes{}",status);
                        int count = 1;
                        for (int i = status.length(); i > 0; i--) {
                            //log.info("into" + i);
                            Ddc doOne= ddcRepository.findByDdcAndDoIdAndDcuAndGateway_Id(ddc.getDdc(),count,null,tbGateway.getId());
                            ////    log.info("DoOn{}", doOne.getId());
                            ////   log.info("State{}", Integer.parseInt(String.valueOf(status.charAt(i - 1))));
                            if (doOne == null){

                            }else {
                                if (doOne.getDoStatus() == null || doOne.getDoStatus() != Integer.parseInt(String.valueOf(status.charAt(i - 1)))) {
                                    //log.info("Dosave");
                                    doOne.setDoStatus(Integer.parseInt(String.valueOf(status.charAt(i - 1))));
                                    ddcSave(doOne);
                                }
                            }
                            //log.info("char{}", status.charAt(i - 1));
                            count++;
                        }

                        ////log.info("{}",status.charAt(ddc.getDi()-1));

                        break;
                    }
                    if (sleepCnt > waitTime*10) {// 통신 안됨
                        if(!connect){
                            connect =true;
                            break;
                        }else{
                            for (int i = 8 ; i > 0; i--) {
                                Ddc ddc1 = ddcRepository.findByDdcAndDoIdAndDcuAndGateway_Id(ddc.getDdc(),i,null,tbGateway.getId());
                                if (ddc1 != null ) {
                                    if (!ddc1.getDoStatus().equals(null)) {
                                        //log.info("save");
                                        ddc1.setDoStatus(null);
                                        ddcSave(ddc1);
                                    }
                                }
                            }
                            break;
                        }

                    }
                    Thread.sleep(100);

                }
                echoClientHandler.channelReadComplete();
            } catch (Exception e) {
                try {
                    echoClientHandler.channelReadComplete();
                } catch (Exception exception) {

                }
            }
        }
    }

    public void  doScanSub1(TbGateway tbGateway , DdcMapper ddc ,ByteBuf buf){
        EchoClientHandler echoClientHandler = new EchoClientHandler();
        buf.writeByte(0x00);
        buf.writeByte(0x03); //명령어 single tan?
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x06);
        buf.writeByte(ddc.getDdc()); // ddc 입력
        buf.writeByte(0x03); //?
        buf.writeByte(0x03);
        buf.writeByte(0x20); //명령어
        buf.writeByte(0x00);
        buf.writeByte(0x06); //데이터 공간
        echoClientHandler.setResultSlaveStatus(null);
        EchoClient echoClient = new EchoClient(ddc.getIpAddress(),ddc.getPort());


        try {
            echoClient.getDataReq(buf,ddc.getDdc(),ddcRepository);
            Thread.sleep(100);
            int sleepCnt = 0;
            ByteBuf response = null;
            while (true) {
                sleepCnt++;
                response = new EchoClientHandler().getResultSlaveStatus();
                //log.info("{}", response);
                if (response != null) {

                    insertdom(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(10) & 0xff), 16)))),ddc,700);
                    insertdom(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(12) & 0xff), 16)))),ddc,701);
                    insertdom(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(14) & 0xff), 16)))),ddc,702);
                    insertdom(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(16) & 0xff), 16)))),ddc,703);
                    insertdom(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(18) & 0xff), 16)))),ddc,704);
                    insertdom(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(20) & 0xff), 16)))),ddc,705);


                    break;
                }
                if (sleepCnt > waitTime *10 ) {// 통신 안됨
                    if(!connect){
                        connect =true;
                        break;
                    }else{
                        List<Ddc> ddc1 = ddcRepository.findByDdcAndGateway_IdAndDcuIsNotNull(ddc.getDdc(),tbGateway.getId());
                        for(Ddc ddc2 :  ddc1) {
                            ddc2.setDoStatus(null);
                            ddcSave(ddc2);
                            if (ddc2.getUse().equals("YES")) {
                                sendMessage(ddc2);
                            }
                        }
                        break;

                    }

                }
                Thread.sleep(100);

            }
            echoClientHandler.channelReadComplete();

        } catch (Exception e) {
            try {
                echoClientHandler.channelReadComplete();
            } catch (Exception exception) {

            }
        }
    }
    public void  doScanSub2(TbGateway tbGateway , DdcMapper ddc,ByteBuf buf){
        EchoClientHandler echoClientHandler = new EchoClientHandler();
        buf.writeByte(0x00);
        buf.writeByte(0x03); //명령어 single tan?
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x06);
        buf.writeByte(ddc.getDdc()); // ddc 입력
        buf.writeByte(0x03); //?
        buf.writeByte(0x03);
        buf.writeByte(0x26); //명령어
        buf.writeByte(0x00);
        buf.writeByte(0x06); //데이터 공간
        echoClientHandler.setResultSlaveStatus(null);
        EchoClient echoClient = new EchoClient(ddc.getIpAddress(),ddc.getPort());


        try {
            echoClient.getDataReq(buf,ddc.getDdc(),ddcRepository);
            Thread.sleep(100);
            int sleepCnt = 0;
            ByteBuf response = null;
            while (true) {
                sleepCnt++;
                response = new EchoClientHandler().getResultSlaveStatus();
                //log.info("{}", response);
                if (response != null) {

                    insertdom(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(10) & 0xff), 16)))),ddc,706);
                    insertdom(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(12) & 0xff), 16)))),ddc,707);
                    insertdom(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(14) & 0xff), 16)))),ddc,708);
                    insertdom(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(16) & 0xff), 16)))),ddc,709);
                    insertdom(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(18) & 0xff), 16)))),ddc,710);
                    insertdom(String.format("%08d",Integer.parseInt(Integer.toBinaryString(Integer.parseInt(String.format("%02x", response.getByte(20) & 0xff), 16)))),ddc,711);


                    break;
                }
                if (sleepCnt > waitTime * 10 ) {// 통신 안됨
                    if(!connect){
                        connect =true;
                        break;
                    }else{
                        List<Ddc> ddc1 = ddcRepository.findByDdcAndGateway_IdAndDcuIsNotNull(ddc.getDdc(),tbGateway.getId());
                        for(Ddc ddc2 :  ddc1) {
                            ddc2.setDoStatus(null);
                            ddcSave(ddc2);
                            if (ddc2.getUse().equals("YES")) {
                                sendMessage(ddc2);
                            }
                        }
                        break;

                    }

                }
                Thread.sleep(100);

            }
            echoClientHandler.channelReadComplete();

        } catch (Exception e) {
            try {
                echoClientHandler.channelReadComplete();
            } catch (Exception exception) {

            }
        }
    }

    public void domscan(  List<DdcMapper> ddcMList  ,ByteBuf buf){

        for (DdcMapper ddc : ddcMList) { //DO 상태값 저장
            TbGateway tbGateway = gatewayRepository.findByIpAddress(ddc.getIpAddress());
            doScanSub1(tbGateway,ddc,buf);
            doScanSub2(tbGateway,ddc,buf);
        }
    }


    @Override
    public void stateChek() {

        ByteBuf buf = Unpooled.buffer();
        List<DataGroupMapper> dcp = dataGroupRepository.findgroup();
        fiveminutescan(dcp,buf);
        totalpower(dcp,buf);



    }

    public void dataOnOffMultiCust3(List<DataCallRequest> dataCallRequests,Integer second){


        for(DataCallRequest dataCallRequest : dataCallRequests){ // 배열로 아이디 배열을 만든후
            try {
                Thread.sleep(second*1000);
                connect = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (dataCallRequest.getDcu() == null || dataCallRequest.getDcu() < 700 ) {
                StringBuilder builder = new StringBuilder();
                for (int i = 8; i > 0; i--) {
                    int DataCount = 0;

                    if (dataCallRequest.getDoId() == i) {
                        //  dataOnOff(dataReq);
                        builder.append(dataCallRequest.getOnoffstatus() == null ? "0" : dataCallRequest.getOnoffstatus());
                        Ddc ddc = ddcRepository.findByDdcAndDoIdAndDcu(dataCallRequest.getDdc(), i, null);
                        ddc.setDoStatus(dataCallRequest.getOnoffstatus() == null ? 0 : dataCallRequest.getOnoffstatus());
                        ddcRepository.save(ddc);
                        DataCount++;
                    }

                    // 같은게 없으면 해당 상태를 찾아서 없으면 0 있으면 해당 상태를 집어 넣는다.
                    if (DataCount == 0) {
                        Ddc ddc = ddcRepository.findByDdcAndDoIdAndDcu(dataCallRequest.getDdc(), i, null);
                        if (ddc == null) {
                            builder.append("0");
                        } else {
                            builder.append(ddc.getDoStatus() == null ? "0" : ddc.getDoStatus());
                        }
                    }
                }
                //log.info(builder.toString());
                Integer temp = Integer.parseInt(builder.toString(), 2); // 변환

                DdcMapper ddcMapper =  ddcRepository.getGroupMapperDdc(dataCallRequest.getId());
                sendOnoff(ddcMapper, temp);
            }else{
                StringBuilder builder = new StringBuilder();
                for (int i = 8; i > 0; i--) {
                    int DataCount = 0;

                    if (dataCallRequest.getDoId() == i) {
                        //  dataOnOff(dataReq);
                        builder.append(dataCallRequest.getOnoffstatus() == null ? "0" : dataCallRequest.getOnoffstatus());
                        Ddc ddc = ddcRepository.findByDdcAndDoIdAndDcu(dataCallRequest.getDdc(), i, dataCallRequest.getDcu());
                        ddc.setDoStatus(dataCallRequest.getOnoffstatus() == null ? 0 : dataCallRequest.getOnoffstatus());
                        ddcRepository.save(ddc);
                        DataCount++;
                    }

                    // 같은게 없으면 해당 상태를 찾아서 없으면 0 있으면 해당 상태를 집어 넣는다.
                    if (DataCount == 0) {
                        Ddc ddc = ddcRepository.findByDdcAndDoIdAndDcu(dataCallRequest.getDdc(), i, dataCallRequest.getDcu());
                        if (ddc == null) {
                            builder.append("0");
                        } else {
                            builder.append(ddc.getDoStatus() == null ? "0" : ddc.getDoStatus());
                        }
                    }
                }
                Integer temp = Integer.parseInt(builder.toString(),2);
                DdcMapper ddcMapper =  ddcRepository.getGroupMapperDcu(dataCallRequest.getId());
                sendSlaveOnoff(ddcMapper,temp,ddcMapper.getDcu()+100);
            }
        }

    }

    @Override
    public  List<DdcResponseDto> datacall() {
        try{



        PageSet pageSet = pageSetRepository.findById(1l).orElse(null);
            if (pageSet == null){
                waitTime = 1;
            }else{
                waitTime =  pageSet.getWaitTime() == null ? 1 :pageSet.getWaitTime();
            }

        final int[] five = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};

        final int[] fifteen = {0, 15, 30, 45};


        ByteBuf buf = Unpooled.buffer();

        // if (( LocalTime.now().getMinute() == 0 || LocalTime.now().getMinute() == 15 || LocalTime.now().getMinute() == 30 || LocalTime.now().getMinute() == 45 )){
        int[] timelist;
        if (pageSet == null || pageSet.getGraphTime() == 15) {
            timelist = fifteen;
        } else {
            timelist = five;
        }
        //System.out.println("Time===============:"+LocalTime.now().getMinute()+IntStream.of(timelist).anyMatch(x -> x == LocalTime.now().getMinute()));
        if (IntStream.of(timelist).anyMatch(x -> x == LocalTime.now().getMinute())) {
            if (saving == false) {
                saving = true;
                stateChek();
            }
        } else {
            saving = false;
        }

        List<DdcGroupMapper> ddcDiList = ddcRepository.getDdcDiGroup();
        List<DdcGroupMapper> ddcDoList = ddcRepository.getDdcDoGroup();
        List<DdcMapper> ddcMdiList = ddcRepository.getGroupMDdcDi();
        List<DdcMapper> ddcMdoList = ddcRepository.getGroupMDdcDo();
        List<DataGroupMapper> dataLists = dataGroupRepository.findgroup();
        for (DataGroupMapper dataList : dataLists) {

            if (!(dataList.getScdataid() == null)) {

                ScData scData = scDataRepository.findById(dataList.getScdataid()).orElse(null);
                if (scData.getUseYn().equals("YES")) {
                    if (scData.getStartDate().isBefore(LocalDate.now()) && scData.getEndDate().isAfter(LocalDate.now())) {

                        if (getDateDayName(LocalDate.now().toString(), scData.getWeeks())) {

                            if (LocalTime.now().getHour() == Integer.parseInt(scData.getHour())) {

                                if (LocalTime.now().getMinute() == Integer.parseInt(scData.getMin())) {
                                    ArrayList<DataCallRequest> arrayList = new ArrayList();
                                    List<DataGroup> dataGroups = dataGroupRepository.findByScDateId(scData.getId());
                                    for (DataGroup dataGroup : dataGroups) {
                                        //System.out.println(dataGroup.getDdcId().getId());
                                        Ddc ddc = ddcRepository.findById(dataGroup.getDdcId().getId()).orElse(null);

                                        DataCallRequest dataCallRequest = new DataCallRequest();
                                        dataCallRequest.setId(ddc.getId());
                                        dataCallRequest.setOnoffstatus(scData.getOnOff().equals("ON") ? 1 : 0);
                                        dataCallRequest.setDdc(ddc.getDdc());
                                        dataCallRequest.setDcu(ddc.getDcu());
                                        dataCallRequest.setDoId(ddc.getDoId());
                                        arrayList.add(dataCallRequest);
                                    }
                                    dataOnOffMultiCust3(arrayList, scData.getWaitTime());


                                }
                            }
                        }
                    }
                }
            }
        }


        //System.out.println(ddcList.size());

        if (connectCount == 0) {
            discan(ddcDiList, buf);
            doscan(ddcDoList, buf);
            dimscan(ddcMdiList, buf);
            domscan(ddcMdoList, buf);
            dcpScan();
        }else{
            if(connectCount > 4){
                connectCount = 0;
            }else{
                connectCount++;
            }
        }
        ////System.out.println("Time===============:"+LocalTime.now().getMinute());
            List<Ddc> dcpList = ddcRepository.findAll();
            List<DdcResponseDto> dcpResponseDtoList
                    = dcpList.stream().map(DdcResponseDto::of)
                    .collect(Collectors.toList());
            return dcpResponseDtoList;
    }catch(Exception e){
            log.info(e.getMessage());
            List<Ddc> dcpList = ddcRepository.findAll();
            List<DdcResponseDto> dcpResponseDtoList
                    = dcpList.stream().map(DdcResponseDto::of)
                    .collect(Collectors.toList());
            return dcpResponseDtoList;

        }

    }

    @Transactional
    public void ddcSave(Ddc ddc){
        ddcRepository.save(ddc);
    }

    public static boolean getDateDayName(String date,String week)  {


        String day = "" ;
        boolean result = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
        Date nDate = null;
        try {
            nDate = dateFormat.parse(date);
        } catch (ParseException e) {
            return result;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(nDate);
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);

        switch(dayNum){
            case 1:
                result = week.charAt(0) == '0'? false : true;
                break;

            case 2:
                result = week.charAt(1) == '0'? false : true;
                break ;
            case 3:
                result = week.charAt(2) == '0'? false : true;
                break ;
            case 4:
                result = week.charAt(3) == '0'? false : true;
                break ;
            case 5:
                result = week.charAt(4) == '0'? false : true;
                break ;
            case 6:
                result = week.charAt(5) == '0'? false : true;
                break ;
            case 7:
                result = week.charAt(6) == '0'? false : true;
                break ;
        }

        return result ;
    }


    @Override
    public void dataOnOff(DataCallRequest dataCallRequest) {

        Ddc dolist= ddcRepository.findById(dataCallRequest.getId()).orElse(null);
        ByteBuf buf = Unpooled.buffer();
        EchoClientHandler echoClientHandler = new EchoClientHandler();
        buf.writeByte(0x00);
        buf.writeByte(0x05); //명령어 single tan?
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x06);
        buf.writeByte(dolist.getDdc()); // ddc 입력
        buf.writeByte(0x05); //?
        buf.writeByte(0x00);
        buf.writeByte(dolist.getDoId()-1); //명령어
        if (dataCallRequest.getOnoffstatus() == 0)
            buf.writeByte(0x00);
        else{
            buf.writeByte(0xff);
        }
        buf.writeByte(0x00); //데이터 공간
        echoClientHandler.setResultStatus(null);
        EchoClient echoClient = new EchoClient(dolist.getGateway().getIpAddress(),dolist.getGateway().getPort());
        echoClient.getDataReq(buf,dolist.getDdc(),ddcRepository);

    }

    @Override
    public void dataOnOffMulti(List<DataCallRequest> dataCallRequests) {
        List<Long> idlist = new ArrayList<>();

        for(DataCallRequest dataCallRequest : dataCallRequests){ // 배열로 아이디 배열을 만든후
            idlist.add(dataCallRequest.getId());
        }
        //ddc list 를 만든다.

        //connectCount = 1;
        List<DdcMapper> ddcMappers =  ddcRepository.getDdcGroupList(idlist);
        for(DdcMapper ddcMapper: ddcMappers ){
            try {
                Thread.sleep(1000);
                connect = false;
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }

            List<DataCallRequest> ddcReqList = new ArrayList<>();
            if(ddcMapper.getDdc() > 207 ){ // dcu 인것만 208~218
                int[] slaveList = new int[12];
                for (int slaveNum = 0 ; slaveNum<12; slaveNum ++ ){ // 슬래이브 0~11 만들기
                    List<Ddc> ddcSlave =  ddcRepository.findByDdcAndDcu(ddcMapper.getDdc(),700+slaveNum); // 쿼리에서 있는지 확인후
                    if (ddcSlave.size() == 0){ // 없으면 그냥 0 입력
                        slaveList[slaveNum] = 0;
                    }else{ // 있으면 salve 생성
                        StringBuilder builder = new StringBuilder();
                        for(DataCallRequest dataCallRequest:dataCallRequests){ // for문 돌면서 화면에서 넘어온 데이터 입력
                            //log.info("{}", ddcSlave.get(0).getDdc());
                            //log.info("{}", ddcSlave.get(0).getDcu());
                            int a = dataCallRequest.getDdc();
                            int b = ddcSlave.get(0).getDdc();
                            int c =  dataCallRequest.getDcu();
                            int d = ddcSlave.get(0).getDcu();
                            if ((a== b) && (c==d)){
                                ddcReqList.add(dataCallRequest);
                            }
                        } //ddc와 dcu 가 같은 것만 추려 내기

                        for (int i = 8; i > 0; i--) { ///1개의 슬래이브 생성
                            int DataCount = 0;
                            for (DataCallRequest  dataReq : ddcReqList ) {// 돌면서 do 와 i 가 같으면 builder 에 넣고

                                if (dataReq.getDoId() == i) {
                                    builder.append(dataReq.getOnoffstatus().toString());
                                    DataCount++;
                                }
                            }
                            // 같은게 없으면 해당 상태를 찾아서 없으면 0 있으면 해당 상태를 집어 넣는다.
                            if (DataCount == 0) {
                                Ddc ddc = ddcRepository.findByDdcAndDoIdAndDcu(ddcMapper.getDdc(),i,ddcMapper.getDcu());
                                if (ddc == null){
                                    builder.append("0");
                                }else {
                                    builder.append(ddc.getDoStatus() == null ? "0" : ddc.getDoStatus());
                                }
                            }
                        }
                        slaveList[slaveNum] = Integer.parseInt(builder.toString(),2);
                    }

                }
                sendDcuOnoff(ddcMapper.getDdc(),slaveList);
            }else{  // dcu 아닌거
                StringBuilder builder = new StringBuilder();
                for(DataCallRequest dataCallRequest:dataCallRequests){
                    if (dataCallRequest.getDdc() == ddcMapper.getDdc()){
                        ddcReqList.add(dataCallRequest);
                    }
                } //ddc가 같은 것만 추려 내기
                for (int i = 8; i > 0; i--) {
                    int DataCount = 0;
                    for (DataCallRequest dataReq : ddcReqList) {// 돌면서 do 와 i 가 같으면 builder 에 넣고

                        if (dataReq.getDoId() == i) {
                            builder.append(dataReq.getOnoffstatus().toString());
                            DataCount++;
                        }
                    }
                    // 같은게 없으면 해당 상태를 찾아서 없으면 0 있으면 해당 상태를 집어 넣는다.
                    if (DataCount == 0) {
                        Ddc ddc = ddcRepository.findByDdcAndDoId(ddcMapper.getDdc(), i);
                        if (ddc == null) {
                            builder.append("0");
                        } else {
                            builder.append(ddc.getDoStatus() == null ? "0" : ddc.getDoStatus());
                        }
                    }
                }
                //System.out.println();
                //log.info(builder.toString());
                Integer temp = Integer.parseInt(builder.toString(), 2); // 변환
                //log.info(temp.toString());
                sendOnoff(ddcMapper,temp);
            }

        }







    }


    @Override
    public void dataOnOffMultiCust(List<DataCallRequest> dataCallRequests) {
        List<Long> idlist = new ArrayList<>();

        for(DataCallRequest dataCallRequest : dataCallRequests){ // 배열로 아이디 배열을 만든후
                idlist.add(dataCallRequest.getId());
        }

        //ddc list 를 만든다.
        List<DdcMapper> ddcMappers =  ddcRepository.getDdcGroupList(idlist);


        for(DdcMapper ddcMapper: ddcMappers ){
            try {
                Thread.sleep(1000);
                connect = false;
                connectCount = 1;
            } catch (InterruptedException e) {
               // e.printStackTrace();
            }
            //System.out.println(ddcMapper.getDcu());
            //System.out.println(ddcMapper.getDdc());
            List<DataCallRequest> ddcReqList = new ArrayList<>();
            Integer getdcu = ddcMapper.getDcu();
            if (ddcMapper.getDcu() == null || ddcMapper.getDcu() < 700 ) {
                StringBuilder builder = new StringBuilder();
                for(DataCallRequest dataCallRequest:dataCallRequests) {
                    int a = dataCallRequest.getDdc();
                    int b = ddcMapper.getDdc();
                    Integer c = dataCallRequest.getDcu();

                    if ((a == b) && (c == null)) {
                        ddcReqList.add(dataCallRequest);
                    } //ddc가 같은 것만 추려 내기
                }
                ddcReqList
                        .stream()
                        .sorted((object1, object2) -> object1.getDoId().compareTo(object2.getDoId()));
                log.info("{}",ddcReqList);
                for (int i = 8; i > 0; i--) {
                    int DataCount = 0;
                    for (DataCallRequest dataReq : ddcReqList) {// 돌면서 do 와 i 가 같으면 builder 에 넣고

                        if (dataReq.getDoId() == i) {
                            builder.append(dataReq.getOnoffstatus() == null ? "0" : dataReq.getOnoffstatus());
                            DataCount++;
                        }
                    }
                    // 같은게 없으면 해당 상태를 찾아서 없으면 0 있으면 해당 상태를 집어 넣는다.
                    if (DataCount == 0) {
                        Ddc ddc = ddcRepository.findByDdcAndDoIdAndDcu(ddcMapper.getDdc(), i,null);
                        if (ddc == null) {
                            builder.append("0");
                        } else {
                            builder.append(ddc.getDoStatus() == null ? "0" : ddc.getDoStatus());
                        }
                    }
                }

                //log.info(builder.toString());
                Integer temp = Integer.parseInt(builder.toString(), 2); // 변환
                //log.info(temp.toString());
                sendOnoff(ddcMapper,temp);
            }else { // dcu 가 없는 것들
                List<Ddc> ddcSlave =  ddcRepository.findByDdcAndDcu(ddcMapper.getDdc(),ddcMapper.getDcu()); // 쿼리에서 있는지 확인후
                StringBuilder builder = new StringBuilder();
                for(DataCallRequest dataCallRequest:dataCallRequests){ // for문 돌면서 화면에서 넘어온 데이터 입력
                    //log.info("{}", ddcSlave.get(0).getDdc());
                    //log.info("{}", ddcSlave.get(0).getDcu());
                    int a = dataCallRequest.getDdc();
                    int b = ddcSlave.get(0).getDdc();
                    int c = dataCallRequest.getDcu() == null ? 0 : dataCallRequest.getDcu();
                    int d = ddcSlave.get(0).getDcu();
                    if ((a== b) && (c==d) && c > 0){
                        ddcReqList.add(dataCallRequest);
                    }
                } //ddc와 dcu 가 같은 것만 추려 내기
                ddcReqList
                        .stream()
                        .sorted((object1, object2) -> object1.getDoId().compareTo(object2.getDoId()));
                log.info("{}",ddcReqList);
                for (int i = 8; i > 0; i--) { ///1개의 슬래이브 생성
                    int DataCount = 0;
                    for (DataCallRequest  dataReq : ddcReqList ) {// 돌면서 do 와 i 가 같으면 builder 에 넣고

                        if (dataReq.getDoId() == i) {
                            builder.append(dataReq.getOnoffstatus().toString());
                            DataCount++;
                        }
                    }
                    // 같은게 없으면 해당 상태를 찾아서 없으면 0 있으면 해당 상태를 집어 넣는다.
                    if (DataCount == 0) {
                        Ddc ddc = ddcRepository.findByDdcAndDoIdAndDcu(ddcMapper.getDdc(),i,ddcMapper.getDcu());
                        if (ddc == null){
                            builder.append("0");
                        }else {
                            builder.append(ddc.getDoStatus() == null ? "0" : ddc.getDoStatus());
                        }
                    }
                }
                Integer temp = Integer.parseInt(builder.toString(),2);
                sendSlaveOnoff(ddcMapper,temp,ddcMapper.getDcu()+100);

            }
        }//반복문 끝
    }

/*
    public void dataOnOffMultiCust2(List<DataCallRequest> dataCallRequests,Integer second){
        List<Long> idlist = new ArrayList<>();

        for(DataCallRequest dataCallRequest : dataCallRequests){ // 배열로 아이디 배열을 만든후

            idlist.add(dataCallRequest.getId());
            //System.out.println(dataCallRequest.getId());
        }
        connect = false;
        //ddc list 를 만든다.
        try {
            Thread.sleep(second*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<DdcMapper> ddcMappers =  ddcRepository.getDdcGroupList(idlist);
        for(DdcMapper ddcMapper: ddcMappers ){ //ddc 기기 갯수 ex)100 ,208

            List<DataCallRequest> ddcReqList = new ArrayList<>();

            if (ddcMapper.getDcu() == null || ddcMapper.getDcu() < 700 ) { //ddc
                int b = ddcMapper.getDdc();

                StringBuilder builder = new StringBuilder();
                for (DataCallRequest dataCallRequest : dataCallRequests) {

                    int a = dataCallRequest.getDdc();

                    if (a == b) {
                        ddcReqList.add(dataCallRequest);
                    } //ddc가 같은 것만 추려 내기


                }
                int count =  0;
                for (DataCallRequest dataReqs : ddcReqList) {
                    count++;
                    for (int i = 8; i > 0; i--) {
                        int DataCount = 0;
                        for (DataCallRequest dataReq : ddcReqList) {// 돌면서 do 와 i 가 같으면 builder 에 넣고
                            //System.out.println(dataReq);
                            if (dataReq.getDoId() == i) {

                                //  dataOnOff(dataReq);
                                builder.append(dataReq.getOnoffstatus() == null ? "0" : dataReq.getOnoffstatus());
                                DataCount++;
                            }
                        }

                        // 같은게 없으면 해당 상태를 찾아서 없으면 0 있으면 해당 상태를 집어 넣는다.
                        if (DataCount == 0) {
                            Ddc ddc = ddcRepository.findByDdcAndDoIdAndDcu(ddcMapper.getDdc(), i, null);
                            if (ddc == null) {
                                builder.append("0");
                            } else {
                                builder.append(ddc.getDoStatus() == null ? "0" : ddc.getDoStatus());
                            }
                        }
                    }

                    //log.info(builder.toString());
                    Integer temp = Integer.parseInt(builder.toString(), 2); // 변환
                    //log.info(temp.toString());
                    Thread.sleep(second * 1000);
                    sendOnoff(ddcMapper, temp);
                }
            }else { // dcu 가 있는 것들
                List<Ddc> ddcSlave =  ddcRepository.findByDdcAndDcu(ddcMapper.getDdc(),ddcMapper.getDcu()); // 쿼리에서 있는지 확인후
                StringBuilder builder = new StringBuilder();
                for(DataCallRequest dataCallRequest:dataCallRequests){ // for문 돌면서 화면에서 넘어온 데이터 입력
                    //log.info("{}", ddcSlave.get(0).getDdc());
                    //log.info("{}", ddcSlave.get(0).getDcu());
                    int a = dataCallRequest.getDdc();
                    int b = ddcSlave.get(0).getDdc();
                    int c = dataCallRequest.getDcu() == null ? 0 : dataCallRequest.getDcu();
                    int d = ddcSlave.get(0).getDcu();
                    if ((a== b) && (c==d) && c > 0){
                        ddcReqList.add(dataCallRequest);
                    }
                } //ddc와 dcu 가 같은 것만 추려 내기

                for (int i = 8; i > 0; i--) { ///1개의 슬래이브 생성
                    int DataCount = 0;
                    for (DataCallRequest  dataReq : ddcReqList ) {// 돌면서 do 와 i 가 같으면 builder 에 넣고

                        if (dataReq.getDoId() == i) {
                            builder.append(dataReq.getOnoffstatus().toString());
                            DataCount++;
                        }
                    }
                    // 같은게 없으면 해당 상태를 찾아서 없으면 0 있으면 해당 상태를 집어 넣는다.
                    if (DataCount == 0) {
                        Ddc ddc = ddcRepository.findByDdcAndDoIdAndDcu(ddcMapper.getDdc(),i,ddcMapper.getDcu());
                        if (ddc == null){
                            builder.append("0");
                        }else {
                            builder.append(ddc.getDoStatus() == null ? "0" : ddc.getDoStatus());
                        }
                    }
                }
                //System.out.println(builder.toString());
                Integer temp = Integer.parseInt(builder.toString(),2);
                sendSlaveOnoff(ddcMapper,temp,ddcMapper.getDcu()+100);

            }
        }//반복문 끝

    }

*/

    void sendSlaveOnoff(DdcMapper ddc ,Integer temp,Integer dcu){
        ByteBuf buf = Unpooled.buffer();
        EchoClientHandler echoClientHandler = new EchoClientHandler();

        buf.writeByte(0x00);
        buf.writeByte(0x08); //명령어 single tan?
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x09);
        buf.writeByte(ddc.getDdc()); // ddc 입력
        buf.writeByte(0x10); //?
        // buf.writeByte(dcu);
        buf.writeByte(0x03); //명령어
        if (dcu == 800){
            buf.writeByte(0x20); //명령어
        }else if(dcu == 801){
            buf.writeByte(0x21); //명령어
        }else if(dcu == 802){
            buf.writeByte(0x22); //명령어
        }else if(dcu == 803){
            buf.writeByte(0x23); //명령어
        }else if(dcu == 804){
            buf.writeByte(0x24); //명령어
        }else if (dcu == 805){
            buf.writeByte(0x25); //명령어
        }else if (dcu == 806){
            buf.writeByte(0x26); //명령어
        }else if (dcu == 807){
            buf.writeByte(0x27); //명령어
        }else if (dcu == 808) {
            buf.writeByte(0x28); //명령어
        }else if (dcu == 809) {
            buf.writeByte(0x29); //명령어
        }else if (dcu ==810) {
            buf.writeByte(0x2a); //명령어
        }else if (dcu == 811) {
            buf.writeByte(0x2b); //명령어
        }
        buf.writeByte(0x00);
        buf.writeByte(0x01); //데이터 공간
        buf.writeByte(0x02);
        buf.writeByte(0x00);
        buf.writeByte(temp);//값들어가는곳 255까지
        echoClientHandler.setResultStatus(null);
        EchoClient echoClient = new EchoClient(ddc.getIpAddress(),ddc.getPort());
        echoClient.getDataReq(buf,ddc.getDdc(),ddcRepository);
        try {
            echoClientHandler.channelReadComplete();
        } catch (Exception e) {
            //System.out.println(e.toString());
        }
    }

    void sendDcuOnoff(Integer ddcnum ,int[] temp){

        ByteBuf buf = Unpooled.buffer();
        EchoClientHandler echoClientHandler = new EchoClientHandler();
        buf.writeByte(0x00);
        buf.writeByte(0x08); //명령어 single tan?
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x1d);
        buf.writeByte(ddcnum); // ddc 입력
        buf.writeByte(0x10); //?
        buf.writeByte(0x03);
        buf.writeByte(0x20); //명령어
        buf.writeByte(0x00);
        buf.writeByte(0x0b); //데이터 공간
        buf.writeByte(0x16);
        buf.writeByte(0x00);
        buf.writeByte(temp[0]);//800
        buf.writeByte(0x00);
        buf.writeByte(temp[1]);//801
        buf.writeByte(0x00);
        buf.writeByte(temp[2]);//802
        buf.writeByte(0x00);
        buf.writeByte(temp[3]);//803
        buf.writeByte(0x00);
        buf.writeByte(temp[4]);//804
        buf.writeByte(0x00);
        buf.writeByte(temp[5]);//805
        buf.writeByte(0x00);
        buf.writeByte(temp[6]);//806
        buf.writeByte(0x00);
        buf.writeByte(temp[7]);//807
        buf.writeByte(0x00);
        buf.writeByte(temp[8]);//808
        buf.writeByte(0x00);
        buf.writeByte(temp[9]);//809
        buf.writeByte(0x00);
        buf.writeByte(temp[10]);//810
        buf.writeByte(0x00);
        buf.writeByte(temp[11]);//811
        echoClientHandler.setResultStatus(null);

        //  EchoClient echoClient = new EchoClient(gateway.get().getIpAddress(),502);
        //  echoClient.getDataReq(buf,ddcnum);
    }

    void sendOnoff(DdcMapper ddc ,Integer temp){
        ByteBuf buf = Unpooled.buffer();
        EchoClientHandler echoClientHandler = new EchoClientHandler();
        buf.writeByte(0x00);
        buf.writeByte(0x06); //명령어 single tan?
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x08);
        buf.writeByte(ddc.getDdc()); // ddc 입력
        buf.writeByte(0x0f); //?
        buf.writeByte(0x00);
        buf.writeByte(0x00); //명령어
        buf.writeByte(0x00);
        buf.writeByte(0x08); //데이터 공간
        buf.writeByte(0x01);
        buf.writeByte(temp);
        echoClientHandler.setResultStatus(null);
        EchoClient echoClient = new EchoClient(ddc.getIpAddress(),ddc.getPort());
        echoClient.getDataReq(buf,ddc.getDdc(),ddcRepository);

    }

    String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for (final byte b : a)
            sb.append(String.format("%02x ", b & 0xff));
        return sb.toString();
    }

    void sendMessage(Ddc ddc) {
        try {

            final String encodingType = "utf-8";
            final String boundary = "____boundary____";

            /**************** 문자전송하기 예제 ******************/
            /* "result_code":결과코드,"message":결과문구, */
            /* "msg_id":메세지ID,"error_cnt":에러갯수,"success_cnt":성공갯수 */
                /* 동일내용 > 전송용 입니다.
                /******************** 인증정보 ********************/
            String sms_url = "https://apis.aligo.in/send/"; // 전송요청 URL

            List<DdcUser> ddcUsers = ddcMessageRepository.findDi(Integer.parseInt(ddc.getId().toString()));

            for (DdcUser ddcUser : ddcUsers) {
                Map<String, String> sms = new HashMap<String, String>();

                sms.put("user_id", "1138178031"); // SMS 아이디
                sms.put("key", "sadfopidm14hmt8apv5pigkx84gscvo2"); //인증키

                /******************** 인증정보 ********************/

                /******************** 전송정보 ********************/
                sms.put("msg", "%고객명%님. 안녕하세요. " + ddc.getMessage()); // 메세지 내용
                //sms.put("receiver", "01090217099,01111111112"); // 수신번호
                sms.put("receiver", ddcUser.getPhone()); // 수신번호
                sms.put("destination", ddcUser.getPhone() + "|" + ddcUser.getName()); // 수신인 %고객명% 치환
                //sms.put("sender", "01075779541"); // 발신번호
                sms.put("sender", "15449598"); // 발신번호
                // sms.put("rdate", ""); // 예약일자 - 20161004 : 2016-10-04일기준
                //sms.put("rtime", ""); // 예약시간 - 1930 : 오후 7시30분
                sms.put("testmode_yn", "N"); // Y 인경우 실제문자 전송X , 자동취소(환불) 처리
                sms.put("title", "디에스이앤(주)"); //  LMS, MMS 제목 (미입력시 본문중 44Byte 또는 엔터 구분자 첫라인)2

                String image = "";
                //image = "/tmp/pic_57f358af08cf7_sms_.jpg"; // MMS 이미지 파일 위치

                /******************** 전송정보 ********************/

                MultipartEntityBuilder builder = MultipartEntityBuilder.create();

                builder.setBoundary(boundary);
                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                builder.setCharset(Charset.forName(encodingType));

                for (Iterator<String> i = sms.keySet().iterator(); i.hasNext(); ) {
                    String key = i.next();
                    builder.addTextBody(key, sms.get(key)
                            , ContentType.create("Multipart/related", encodingType));
                }

                File imageFile = new File(image);
                if (image != null && image.length() > 0 && imageFile.exists()) {

                    builder.addPart("image",
                            new FileBody(imageFile, ContentType.create("application/octet-stream"),
                                    URLEncoder.encode(imageFile.getName(), encodingType)));
                }

                HttpEntity entity = builder.build();

                HttpClient client = HttpClients.createDefault();
                HttpPost post = new HttpPost(sms_url);
                post.setEntity(entity);

                HttpResponse res = client.execute(post);

                String result = "";
                if (res != null) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), encodingType));
                    String buffer = null;
                    while ((buffer = in.readLine()) != null) {
                        result += buffer;
                    }
                    in.close();
                }

            }
        } catch (Exception e) {

        }
    }

}
