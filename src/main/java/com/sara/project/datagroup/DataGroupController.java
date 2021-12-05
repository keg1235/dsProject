package com.sara.project.datagroup;

import com.sara.project.datagroup.dto.DataGroupCard;
import com.sara.project.datagroup.dto.DataGroupMapper;
import com.sara.project.datagroup.dto.DataGroupRequestDto;
import com.sara.project.datagroup.dto.DataGroupResponseDto;
import com.sara.project.ddc.DdcService;
import com.sara.project.ddc.dto.DdcResponseDto;
import com.sara.project.ddcmessage.DdcMessageService;
import com.sara.project.ddcmessage.ddcMessageDto.DdcMessageRequestDto;
import com.sara.project.ddcmessage.ddcMessageDto.DdcMessageResponseDto;
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


@Api(tags = {"datagroup"})
@RestController
@RequestMapping("/datagroup")
@CrossOrigin
@Slf4j
public class DataGroupController {

    @Autowired
    private DataGroupService dataGroupService;

    @Autowired
    private DdcService ddcService;

    @Autowired
    private UserService userService;


    @ApiOperation(value = "user 조회")
    @GetMapping("/getddc/{id}")
    public ResponseEntity<ResponseMessage> getGatewayOne(
            @PathVariable Long id
    ) {

        try {
            Map<String,Object> map = new HashMap<String,Object>();

            List<DataGroupResponseDto> dataGroupResponseDtos
                    = dataGroupService.getDataGroupList(id);
            List<DdcResponseDto> ddcResponseDtos
                    = ddcService.getDdcResponseDtoList();

            map.put("datasave",dataGroupResponseDtos);
            map.put("ddc",ddcResponseDtos);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, map, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "user 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getgroup(
            @PathVariable Long id
    ) {

        try {
            List<DataGroupResponseDto> dataGroupResponseDtos
                    = dataGroupService.getDataGroupList(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, dataGroupResponseDtos, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "group 조회")
    @GetMapping("")
    public ResponseEntity<ResponseMessage> getGatewayOne(
    ) {

        try {
            List<DataGroupMapper> dataGroupList
                    = dataGroupService.getDataGroupLists();
            ResponseMessage responseMessage = new ResponseMessage(Const.success, dataGroupList, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "groupcard 조회")
    @GetMapping("/card")
    public ResponseEntity<ResponseMessage> getGatewayCard(
    ) {

        try {
            List<DataGroupCard> dataGroupList
                    = dataGroupService.getDataGroupCard();


            ResponseMessage responseMessage = new ResponseMessage(Const.success, dataGroupList, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "DataGroup 저장")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody List<DataGroupRequestDto> dataGroupRequestDtos

    ) {
        log.info("{}",dataGroupRequestDtos);
        try {
            dataGroupService.listsave(dataGroupRequestDtos);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "DataGroup 삭제")
    @DeleteMapping("{id}")
    public ResponseEntity<ResponseMessage> delete(
            @PathVariable long id

    ) {

        try {
            dataGroupService.delete(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
