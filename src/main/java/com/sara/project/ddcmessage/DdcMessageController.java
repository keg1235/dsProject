package com.sara.project.ddcmessage;

import com.sara.project.ddc.dto.DdcRequestDto;
import com.sara.project.ddcmessage.ddcMessageDto.DdcMessageRequestDto;
import com.sara.project.ddcmessage.ddcMessageDto.DdcMessageResponseDto;
import com.sara.project.gateway.dto.GatewayResponseDto;
import com.sara.project.user.UserService;
import com.sara.project.user.dto.UserResponseDto;
import com.sara.project.util.Const;
import com.sara.project.util.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"ddcmessage"})
@RestController
@RequestMapping("/ddcmessage")
@CrossOrigin
@Slf4j
public class DdcMessageController {

    @Autowired
    private DdcMessageService ddcMessageService;

    @Autowired
    private UserService userService;


    @ApiOperation(value = "user 조회")
    @GetMapping("/getUser/{id}")
    public ResponseEntity<ResponseMessage> getGatewayOne(
            @PathVariable Integer id
    ) {

        try {
            Map<String,Object> map = new HashMap<String,Object>();

            List<DdcMessageResponseDto> ddcMessageResponseDtos
                    = ddcMessageService.getDdcMessageResponseDtos(id);

            List<UserResponseDto> userResponseDtoList
                    = userService.getuseUserResponseDtoList();
            map.put("ddcMessage",ddcMessageResponseDtos);
            map.put("user",userResponseDtoList);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, map, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "DDC 저장")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody List<DdcMessageRequestDto> ddcRequestDtos

    ) {
        log.info("{}",ddcRequestDtos);
        try {
            ddcMessageService.listsave(ddcRequestDtos);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "DDC 삭제")
    @DeleteMapping("{id}")
    public ResponseEntity<ResponseMessage> delete(
            @PathVariable long id

    ) {

        try {
            ddcMessageService.delete(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
