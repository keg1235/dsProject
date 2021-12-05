package com.sara.project.ddc;

import com.sara.project.common.util.CommonCode;
import com.sara.project.common.util.CommonGroupCode;
import com.sara.project.dcp.dto.DcpRequestDto;
import com.sara.project.ddc.dto.DdcRequestDto;
import com.sara.project.ddc.dto.DdcResponseDto;
import com.sara.project.util.Const;
import com.sara.project.util.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"DDC"})
@RestController
@RequestMapping("/ddc")
@CrossOrigin
@Slf4j
public class DdcController {

    @Autowired
    private DdcService ddcService;

    @ApiOperation(value = "DDC 리스트 조회")
    @GetMapping("")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string")
    })
    public ResponseEntity<ResponseMessage> getDdcList(Pageable pageable) {
        try {
            ResponseMessage responseMessage = null;
            if(pageable.getPageNumber() < 1) {
                List<DdcResponseDto> ddcResponseDtos
                        = ddcService.getDdcResponseDtoList();
                responseMessage = new ResponseMessage(Const.success, ddcResponseDtos, "");
            } else {
                Pageable pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
                Page<DdcResponseDto> groupResponseDtoPage
                        = ddcService.getDdcPage(pageRequest);
                responseMessage = new ResponseMessage(Const.success, groupResponseDtoPage, "");
            }
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "DDC 리스트 조회")
    @GetMapping("/di")
    public ResponseEntity<ResponseMessage> getDdcAll() {
        try {
            ResponseMessage responseMessage = null;

                List<DdcResponseDto> ddcResponseDtos
                        = ddcService.getDdcResponseDtoList();
            responseMessage = new ResponseMessage(Const.success, ddcResponseDtos, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "특정 DDC 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getDdcOne(
            @PathVariable Long id
    ) {

        try {
            DdcResponseDto groupResponseDto
                    = ddcService.getDdcResponseDto(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, groupResponseDto, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "특정 DiList 조회")
    @GetMapping("/getDi/{id}")
    public ResponseEntity<ResponseMessage> getdiList(
            @PathVariable Integer id
    ) {

        try {
            List<CommonCode> commonCodes
                    = ddcService.getDiResponseDto(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, commonCodes, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "특정 DDC 조회")
    @GetMapping("/group")
    public ResponseEntity<ResponseMessage> getDdcGroupList(
    ) {

        try {
            List<CommonCode> commonCodes
                    = ddcService.getDdcGroupResponseDto();
            ResponseMessage responseMessage = new ResponseMessage(Const.success, commonCodes, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "그룹")
    @GetMapping("/group/{type}")
    public ResponseEntity<ResponseMessage> getDcuGroupList(
            @PathVariable String type
    ) {
        List<CommonGroupCode> commonCodes;
        try {
            if (type.equals("ddc")){
                commonCodes
                        = ddcService.getDdcGroupResponseDtoCust();
            }else{
                commonCodes
                        = ddcService.getDcuGroupResponseDtoCust();
            }

            ResponseMessage responseMessage = new ResponseMessage(Const.success, commonCodes, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "DDC 저장")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody List<DdcRequestDto> ddcRequestDtos

    ) {
        log.info("{}",ddcRequestDtos);
        try {
            ddcService.save(ddcRequestDtos);
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
            @RequestBody DdcRequestDto ddcRequestDto
    ) {

        try {
            ddcService.update(id, ddcRequestDto);
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
            ddcService.delete(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
