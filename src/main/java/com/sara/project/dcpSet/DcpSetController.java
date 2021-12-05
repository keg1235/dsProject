package com.sara.project.dcpSet;

import com.sara.project.common.util.CommonCode;
import com.sara.project.common.util.CommonGroupCode;
import com.sara.project.dcp.DcpService;
import com.sara.project.dcpSet.dto.DcpGroupDto;
import com.sara.project.dcpSet.dto.DcpSetRequestDto;
import com.sara.project.dcpSet.dto.DcpSetResponseDto;
import com.sara.project.util.Const;
import com.sara.project.util.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"DCPSet"})
@RestController
@RequestMapping("/dcpSet")
@CrossOrigin
@Slf4j
public class DcpSetController {


    @Autowired
    private DcpSetService dcpSetService;


    @ApiOperation(value = "DCPSET 리스트 조회")
    @GetMapping("")
    public ResponseEntity<ResponseMessage> getDcpSetAll() {
        try {
            ResponseMessage responseMessage = null;

            List<DcpSetResponseDto> dcpResponseDtos
                    = dcpSetService.getDcpSetResponseDtoList();
            responseMessage = new ResponseMessage(Const.success, dcpResponseDtos, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "특정 DCPSET 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getDcpSetOne(
            @PathVariable Long id
    ) {

        try {
            DcpSetResponseDto groupResponseDto
                    = dcpSetService.getDcpSetResponseDto(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, groupResponseDto, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

/*
    @ApiOperation(value = "특정 DCPSET 조회")
    @GetMapping("/getDi/{id}")
    public ResponseEntity<ResponseMessage> getdiList(
            @PathVariable Integer id
    ) {

        try {
            List<CommonCode> commonCodes
                    = dcpSetService.getDiResponseDto(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, commonCodes, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "특정 DDC 조회")
    @GetMapping("/group")
    public ResponseEntity<ResponseMessage> getDcpSetGroupList(
    ) {

        try {
            List<CommonCode> commonCodes
                    = dcpSetService.getDcpSetGroupResponseDto();
            ResponseMessage responseMessage = new ResponseMessage(Const.success, commonCodes, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/



    @ApiOperation(value = "DCPSET 저장")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody List<DcpSetRequestDto> dcpSetRequestDtos

    ) {
        log.info("{}",dcpSetRequestDtos);
        try {
            dcpSetService.save(dcpSetRequestDtos);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "DDC 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(
            @PathVariable Long id,
            @RequestBody DcpSetRequestDto dcpSetRequestDto
    ) {

        try {
            dcpSetService.update(id, dcpSetRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "DDC 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(
            @PathVariable Long id
    ) {

        try {
            dcpSetService.delete(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "DCP_GROUP")
    @GetMapping("/group")
    public ResponseEntity<ResponseMessage> getDdcGroupList(
    ) {

        try {
            List<CommonGroupCode> commonCodes
                    = dcpSetService.getDcpGroupResponseDto();
            ResponseMessage responseMessage = new ResponseMessage(Const.success, commonCodes, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "DCP_GROUP")
    @GetMapping("/group/test")
    public ResponseEntity<ResponseMessage> getDdcGrouptestList(
    ) {

        try {
            List<DcpGroupDto> commonCodes
                    = dcpSetService.getDcpGroupDto();
            ResponseMessage responseMessage = new ResponseMessage(Const.success, commonCodes, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
