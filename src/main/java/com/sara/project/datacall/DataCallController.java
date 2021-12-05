package com.sara.project.datacall;


import com.fasterxml.jackson.databind.node.LongNode;
import com.sara.project.EchoClient;
import com.sara.project.EchoClientHandler;
import com.sara.project.company.dto.CompanyRequestDto;
import com.sara.project.company.dto.CompanyResponseDto;
import com.sara.project.datagroup.DataGroup;
import com.sara.project.dcpReport.DcpReport;
import com.sara.project.dcpReport.DcpReportRepository;
import com.sara.project.dcpReport.dto.DcpReportRequestDto;
import com.sara.project.dcpSet.DcpSet;
import com.sara.project.dcpSet.DcpSetRepository;
import com.sara.project.dcpSet.DcpSetService;
import com.sara.project.dcpSet.dto.DcpGroupDto;
import com.sara.project.ddc.Ddc;
import com.sara.project.ddc.DdcRepository;
import com.sara.project.ddc.dto.DdcResponseDto;
import com.sara.project.drawData.dto.DrawDataResponseDto;
import com.sara.project.eletron.Eletron;
import com.sara.project.eletron.dto.EletronRequestDto;
import com.sara.project.gateway.GatewayRepository;
import com.sara.project.gateway.TbGateway;
import com.sara.project.util.Const;
import com.sara.project.util.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/datacall")
@Api(tags = {"데이터 불러오기"})
@CrossOrigin
public class DataCallController {

    @Autowired
    private GatewayRepository gatewayRepository;

    @Autowired
    private DataCallService dataCallService;

    @Autowired
    private DdcRepository ddcRepository;

    @Autowired
    private DcpSetRepository dcpSetRepository;

    @Autowired
    private DcpSetService dcpSetService;

    @Autowired
    private DcpReportRepository dcpReportRepository;


    @ApiOperation(value = "데이터 oNoff ")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> list2(
            @RequestBody List<DataCallRequest> dataCallRequest
    ) throws Exception {

        dataCallService.dataOnOffMultiCust(dataCallRequest);

        try {
            ResponseMessage responseMessage = new ResponseMessage(Const.success,"" , "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "데이터 oNoff ")
    @PostMapping("/draw")
    public ResponseEntity<ResponseMessage> draw(
            @RequestBody List<DrawDataResponseDto> dataCallRequests
    ) throws Exception {

        try {
            List<DataCallRequest> dataCallRequest = new ArrayList<>();

            for (DrawDataResponseDto drawDataResponseDto : dataCallRequests) {
                DataCallRequest callRequest = new DataCallRequest();


                callRequest.setId(drawDataResponseDto.getTypeId());
                Ddc ddc = ddcRepository.findById(drawDataResponseDto.getTypeId()).orElse(null);
                callRequest.setDcu(ddc.getDcu());
                callRequest.setOnoffstatus(Integer.parseInt(drawDataResponseDto.getState()));
                callRequest.setDoId(ddc.getDoId());
                callRequest.setDdc(ddc.getDdc());
                dataCallRequest.add(callRequest);
            }

            dataCallService.dataOnOffMultiCust(dataCallRequest);
        }catch (Exception e){
            log.info("{}",e);
        }

        try {
            ResponseMessage responseMessage = new ResponseMessage(Const.success,"" , "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "데이터 test")
    @PostMapping("/test")
    public ResponseEntity<ResponseMessage> test(){
        int num = 209;
        List<Ddc> dcpList = ddcRepository.findByDdc(num);
        System.out.println(dcpList.size());
        for (Ddc ddc : dcpList){
            ddc.setDiStatus(null);
            ddc.setDoStatus(null);
            ddcRepository.save(ddc);
        }
        try {
            ResponseMessage responseMessage = new ResponseMessage(Const.success,dcpList , "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "데이터 test")
    @PostMapping("/test2")
    public ResponseEntity<ResponseMessage> tes2t() {
        ByteBuf buf = Unpooled.buffer();
        EchoClientHandler echoClientHandler = new EchoClientHandler();
        buf.writeByte(0x00);
        buf.writeByte(0x03); //명령어 single tan?
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x06);
        buf.writeByte(0x01); // ddc 입력
        buf.writeByte(0x03); //?
        buf.writeByte(0x00);
        //buf.writeByte(0x82); //명령어130
        buf.writeByte(0xa2); //명령어162
        buf.writeByte(0x00);
        buf.writeByte(0x04); //데이터 공간
        echoClientHandler.setResulttotalResult(null);
        EchoClient echoClient = new EchoClient("192.168.0.66", 502);
        echoClient.getDataReq(buf, 1,ddcRepository); //바이트버퍼에 넣고
        long accum = 0;
        byte[] test = new byte[8];
        double resultdouble;
        try {
            Thread.sleep(500);
            int sleepCnt = 0;
            ByteBuf response = null;
            while (true) {
                sleepCnt++;
                response = new EchoClientHandler().getResulttotalResult();
              //  log.info("{}", response);
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


                    int i = 0;
                    for ( int shiftBy = 0; shiftBy < 64; shiftBy += 8 ) {
                        accum |= ( (long)( test[i] & 0xff ) ) << shiftBy;
                        i++;
                    }
                    int count = 1;
                    break;
                }
            }
        } catch (Exception e) {

        }
        try {
            ResponseMessage responseMessage = new ResponseMessage(Const.success, ByteBuffer.wrap(test).getDouble() , "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation(value = "데이터 test")
    @PostMapping("/test3")
    public ResponseEntity<ResponseMessage> tes3() {
        String result = "";
        ByteBuf buf = Unpooled.buffer();
        EchoClientHandler echoClientHandler = new EchoClientHandler();
        buf.writeByte(0x00);
        buf.writeByte(0x03); //명령어 single tan?
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x06);
        buf.writeByte(0x01); // ddc 입력
        buf.writeByte(0x03); //?
        buf.writeByte(0x00);
        buf.writeByte(0x0f); //명령어130
        //buf.writeByte(0xa2); //명령어162
        buf.writeByte(0x00);
        buf.writeByte(0x01); //데이터 공간
        echoClientHandler.setResultfive(null);
        EchoClient echoClient = new EchoClient("192.168.0.66", 502);
        echoClient.getDataReq(buf, 1,ddcRepository); //바이트버퍼에 넣고
        long accum = 0;
        byte[] test = new byte[2];
        double resultdouble;
        try {
            Thread.sleep(500);
            int sleepCnt = 0;
            ByteBuf response = null;
            while (true) {
                sleepCnt++;
                response = new EchoClientHandler().getResultfive();
                //  log.info("{}", response);
                if (response != null) {
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

                    int decimal = Integer.parseInt(sb.toString(), 16);

                    ResponseMessage responseMessage = new ResponseMessage(Const.success,decimal , "");
                    return new ResponseEntity<>(responseMessage, HttpStatus.OK);


                }
            }
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @ApiOperation(value = "데이터 호출 1")
    @GetMapping("/breadcrumbs2")
    public ResponseEntity<ResponseMessage> list3() throws Exception {
        TbGateway gateway= gatewayRepository.getOne(1l);
        ByteBuf buf = Unpooled.buffer();
        EchoClientHandler echoClientHandler = new EchoClientHandler();
        buf.writeByte(0x00);
        buf.writeByte(0x06); //명령어 single tan?
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x08);
        buf.writeByte(0xD1); // ddc 입력200
        buf.writeByte(0x0f); //?
        buf.writeByte(0x00);
        buf.writeByte(0x00); //명령어
        buf.writeByte(0x00);
        buf.writeByte(0x08); //데이터 공간
        buf.writeByte(0x01);
        buf.writeByte(0x00);
        echoClientHandler.setResultStatus(null);
        EchoClient echoClient = new EchoClient(gateway.getIpAddress(),gateway.getPort());
        //echoClient.getDataReq(bufddc.getId());
        try {
            ResponseMessage responseMessage = new ResponseMessage(Const.success,"read" , "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "dcpsend")
    @PostMapping("/send/{id}/{num}")
    public ResponseEntity<ResponseMessage> dcpsend(
            @PathVariable Long id,
            @PathVariable Integer num
    ) {
        DcpSet dcpSet = dcpSetRepository.getOne(id);
        ByteBuf buf = Unpooled.buffer();
        EchoClientHandler echoClientHandler = new EchoClientHandler();
        buf.writeByte(0x00);
        buf.writeByte(0x08); //TransFor id?
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x09); // 길이?
        buf.writeByte(dcpSet.getDcp()); // 기기 번호 DCP or DDC
        buf.writeByte(0x10); //function code readCoil , read Holding register

        //buf.writeByte(0x82); //명령어130
        if (dcpSet.getAddress() < 256){
            buf.writeByte(0x00);
            buf.writeByte(dcpSet.getAddress()); //메모리 주소
        }else {
            String cust = Integer.toBinaryString(dcpSet.getAddress());
            String cust2 =  cust.substring(cust.length()-8, cust.length());
            buf.writeByte(Integer.parseInt(cust.substring(0, cust.length()-8), 2));
            buf.writeByte(Integer.parseInt(cust2, 2));
        }
        buf.writeByte(0x00);
        buf.writeByte(0x01);
        buf.writeByte(0x02);
        if (num< 256){
            buf.writeByte(0x00);
            buf.writeByte(num); //메모리 주소
        }else {
            String cust = Integer.toBinaryString(num);
            String cust2 =  cust.substring(cust.length()-8, cust.length());
            buf.writeByte(Integer.parseInt(cust.substring(0, cust.length()-8), 2));
            buf.writeByte(Integer.parseInt(cust2, 2));
        }
        echoClientHandler.setResultStatus(null);
        EchoClient echoClient = new EchoClient(dcpSet.getGateway().getIpAddress(),dcpSet.getGateway().getPort());
        echoClient.getDataReq(buf,dcpSet,dcpSetRepository);
        try {
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "데이터 set test")
    @PostMapping("/dcptest4")
    public ResponseEntity<ResponseMessage> dcptest4() {
        List<DcpSet> dcpSets = dcpSetRepository.findAll();
        String result = "";
        String Value = "";
        for (DcpSet dcpSet : dcpSets){
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

                //buf.writeByte(0x82); //명령어130
                if (dcpSet.getAddress() < 256){
                    buf.writeByte(0x00);
                    buf.writeByte(dcpSet.getAddress()); //메모리 주소
                }else {
                    String cust = Integer.toBinaryString(dcpSet.getAddress());
                    String cust2 =  cust.substring(cust.length()-8, cust.length());
                    buf.writeByte(Integer.parseInt(cust.substring(0, cust.length()-8), 2));
                    buf.writeByte(Integer.parseInt(cust2, 2));

                }
                buf.writeByte(0x00);
                buf.writeByte(dcpSet.getIndexNum()); //데이터 공간
                echoClientHandler.setDcpType("");
                echoClientHandler.setDcpSetBufferResult(null);
                EchoClient echoClient = new EchoClient(dcpSet.getGateway().getIpAddress(), dcpSet.getGateway().getPort());
                echoClient.getDataReq(buf, dcpSet,dcpSetRepository); //바이트버퍼에 넣고
                long accum = 0;

                double resultdouble;
                try {
                    Thread.sleep(500);
                    int sleepCnt = 0;
                    ByteBuf response = null;
                    while (true) {
                        sleepCnt++;
                        response = new EchoClientHandler().getDcpSetBufferResult();
                        //  log.info("{}", response);
                        if (response != null) {
                            int size = response.readableBytes();
                            byte[] test = new byte[size-9];
                            // for문을 돌며 가져온 바이트 값을 연결합니다.
                            int f = 0;
                            log.info("{}",size);
                            log.info("{}",test.length);
                            for (int i = 9; i < size; i++) {
                                test[f] = response.getByte(i);
                                f++;
                            }


                            if(dcpSet.getDataType().equals("Long")){
                                result = Long.valueOf(ByteBuffer.wrap(test).getLong()).toString();
                                 Value = Long.toString (((Long.valueOf(result) - Long.valueOf(dcpSet.getVolumeMin())) / (Long.valueOf(dcpSet.getVolumeMax()) - Long.valueOf(dcpSet.getVolumeMin()))) * (Long.valueOf(dcpSet.getDisplayMax())-Long.valueOf(dcpSet.getDisplayMin())) + Long.valueOf(dcpSet.getDisplayMin()));
                            }else if(dcpSet.getDataType().equals("Word")){
                                //log.info("{}",ByteBuffer.wrap(test).getInt());
                                result = Integer.toString(response.getByte(size-1));
                                log.info("{}",((Integer.valueOf(result).doubleValue() - Integer.valueOf(dcpSet.getVolumeMin())) / (Integer.valueOf(dcpSet.getVolumeMax()) - Integer.valueOf(dcpSet.getVolumeMin()))));
                                Value = Double.toString (((Integer.valueOf(result)*1.0-Integer.valueOf(dcpSet.getVolumeMin())) / (Integer.valueOf(dcpSet.getVolumeMax()) - Integer.valueOf(dcpSet.getVolumeMin()))) * (Integer.valueOf(dcpSet.getDisplayMax())-Integer.valueOf(dcpSet.getDisplayMin())) + Integer.valueOf(dcpSet.getDisplayMin()));
                            }else if(dcpSet.getDataType().equals("Double")){

                                result = BigDecimal.valueOf(ByteBuffer.wrap(test).getDouble()).toString();
                                 Value = Long.toString (((Long.valueOf(result) - Long.valueOf(dcpSet.getVolumeMin())) / (Long.valueOf(dcpSet.getVolumeMax()) - Long.valueOf(dcpSet.getVolumeMin()))) * (Long.valueOf(dcpSet.getDisplayMax())-Long.valueOf(dcpSet.getDisplayMin())) + Long.valueOf(dcpSet.getDisplayMin()));
                            }

                            if ( dcpSet.getValue()==null || !dcpSet.getValue().equals(Value+dcpSet.getUnit())){
                                dcpSet.setValue(Value+dcpSet.getUnit());
                                DcpReport dcpReport =new DcpReportRequestDto().of(dcpReportRepository.findMaxValue()+1,dcpSet);
                                dcpReportRepository.save(dcpReport);
                            }
                            log.info(Value+dcpSet.getUnit());
                            dcpSet.setValue(Value+dcpSet.getUnit());
                            dcpSetRepository.save(dcpSet);
                            // 읽을 수 있는 바이트의 길이만큼 바이트 배열을 초기화합니다.



                            echoClientHandler.channelReadComplete();
                            break;
                        }
                    }
                    if (sleepCnt > 50) {// 통신 안됨gjdmsg


                        break;
                    }



            } catch (Exception e) {
                try {

                    echoClientHandler.channelReadComplete();
                } catch (Exception exception) {
                    log.info("{}",exception);
                    //System.out.println(exception.toString());
                    exception.printStackTrace();
                }
            }


            }else if (dcpSet.getType().equals("DDC")){

            }else if (dcpSet.getType().equals("DCU") ){

            }else if (dcpSet.getType().equals("DCU")){

            }


        }


        try {
            ResponseMessage responseMessage = new ResponseMessage(Const.success, result, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation(value = "d")
    @GetMapping("/testmail")
    public ResponseEntity<ResponseMessage> testMail() {
        try {
            final String encodingType = "utf-8";
            final String boundary = "____boundary____";

            /**************** 문자전송하기 예제 ******************/
            /* "result_code":결과코드,"message":결과문구, */
            /* "msg_id":메세지ID,"error_cnt":에러갯수,"success_cnt":성공갯수 */
                /* 동일내용 > 전송용 입니다.
                /******************** 인증정보 ********************/
            String sms_url = "https://apis.aligo.in/send/"; // 전송요청 URL

            Map<String, String> sms = new HashMap<String, String>();

            sms.put("user_id", "1138178031"); // SMS 아이디
            sms.put("key", "sadfopidm14hmt8apv5pigkx84gscvo2"); //인증키

            /******************** 인증정보 ********************/

            /******************** 전송정보 ********************/
            sms.put("msg", "%고객명%님. 안녕하세요. API TEST SEND"); // 메세지 내용
            //sms.put("receiver", "01090217099,01111111112"); // 수신번호
            sms.put("receiver", "01090217099"); // 수신번호
            sms.put("destination", "01090217099|담당자"); // 수신인 %고객명% 치환
            sms.put("sender", "01075779541"); // 발신번호
           // sms.put("rdate", ""); // 예약일자 - 20161004 : 2016-10-04일기준
            //sms.put("rtime", ""); // 예약시간 - 1930 : 오후 7시30분
            sms.put("testmode_yn", "N"); // Y 인경우 실제문자 전송X , 자동취소(환불) 처리
            sms.put("title", "제목입력"); //  LMS, MMS 제목 (미입력시 본문중 44Byte 또는 엔터 구분자 첫라인)

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
            ResponseMessage responseMessage = new ResponseMessage(Const.success, result, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "데이터 호출 1")
    @GetMapping("/plctest")
    public ResponseEntity<ResponseMessage> plctest() throws Exception {

        ByteBuf buf = Unpooled.buffer();
        EchoClientHandler echoClientHandler = new EchoClientHandler();
        buf.writeByte(0x00);
        buf.writeByte(0x02); //명령어 single tan?
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x00);
        buf.writeByte(0x06);
        buf.writeByte(0x01); // ddc 입력200
        buf.writeByte(0x02); //?
        buf.writeByte(0x00);
        buf.writeByte(0x01); //명령어
        buf.writeByte(0x00);
        buf.writeByte(0x08); //데이터 공간
        echoClientHandler.setResultStatus(null);
        EchoClient echoClient = new EchoClient("192.168.0.10",502);
        echoClient.getDataReq(buf);
        try {
            ResponseMessage responseMessage = new ResponseMessage(Const.success,"read" , "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
