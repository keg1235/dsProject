package com.sara.project.dcp;

import com.sara.project.dcp.dto.DcpRequestDto;
import com.sara.project.dcp.dto.DcpResponseDto;
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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"DCP"})
@RestController
@RequestMapping("/dcp")
@CrossOrigin
@Slf4j
public class DcpController   {

    @Autowired
    private DcpService dcpService;

    @ApiOperation(value = "DCP 리스트 조회")
    @GetMapping("")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string")
    })
    public ResponseEntity<ResponseMessage> getDcpList(Pageable pageable) {
        try {
            ResponseMessage responseMessage = null;
            if(pageable.getPageNumber() < 1) {
                List<DcpResponseDto> DcpResponseDtos
                        = dcpService.getDcpResponseDtoList();
                responseMessage = new ResponseMessage(Const.success, DcpResponseDtos, "");
            } else {
                Pageable pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
                Page<DcpResponseDto> groupResponseDtoPage
                        = dcpService.getDcpPage(pageRequest);
                responseMessage = new ResponseMessage(Const.success, groupResponseDtoPage, "");
            }
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "특정 DCP 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getDcpOne(
            @PathVariable Long id
    ) {

        try {
            DcpResponseDto groupResponseDto
                    = dcpService.getDcpResponseDto(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, groupResponseDto, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "DCP 저장")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody DcpRequestDto DcpRequestDto
    ) {

        try {

            dcpService.save(DcpRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "DCP 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(
            @PathVariable Long id,
            @RequestBody DcpRequestDto dcpRequestDto
    ) {

        try {
            dcpService.update(id, dcpRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "DCP 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(
            @PathVariable Long id
    ) {

        try {
            dcpService.delete(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
