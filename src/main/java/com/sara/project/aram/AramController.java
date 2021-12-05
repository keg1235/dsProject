package com.sara.project.aram;

import com.sara.project.aram.dto.AramRequestDto;
import com.sara.project.aram.dto.AramResponseDto;
import com.sara.project.aram.dto.AramSetRequestDto;
import com.sara.project.aram.dto.AramSetResponseDto;
import com.sara.project.dcp.dto.DcpRequestDto;
import com.sara.project.eletron.dto.EletronResponseDto;
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


@Api(tags = {"Aram 조회"})
@RestController
@RequestMapping("/aram")
@CrossOrigin
@Slf4j
public class AramController {

    @Autowired
    private AramService aramService;


    @ApiOperation(value = "aram 리스트 조회")
    @GetMapping("")
    public ResponseEntity<ResponseMessage> getaram() {
        try {
            ResponseMessage responseMessage = null;

                List<AramResponseDto> aramResponseDtos
                        = aramService.getAramList();
                responseMessage = new ResponseMessage(Const.success, aramResponseDtos, "");

            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "aram 엑셀 조회")
    @GetMapping("/excel")
    public ResponseEntity<ResponseMessage> getaramExcel() {
        try {
            ResponseMessage responseMessage = null;

                List<AramResponseDto> aramResponseDtos
                        = aramService.getAramExcelList();
                responseMessage = new ResponseMessage(Const.success, aramResponseDtos, "");

            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "aramset 조회")
    @GetMapping("/set")
    public ResponseEntity<ResponseMessage> getaramSet() {
        try {
            ResponseMessage responseMessage = null;

            AramSetResponseDto aramResponseDtos
                    = aramService.getAramSetList();
            responseMessage = new ResponseMessage(Const.success, aramResponseDtos, "");

            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "aramset 저장")
    @PostMapping("/set")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody AramSetRequestDto aramSetRequestDto
    ) {

        try {

            aramService.save(aramSetRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "상태값 저장")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> saveStatus(
            @RequestBody AramRequestDto aramRequestDto
    ) {

        try {
            aramService.saveStatus(aramRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "확인 저장")
    @PostMapping("/confirm")
    public ResponseEntity<ResponseMessage> saveClose(
            @RequestBody AramRequestDto aramRequestDto
    ) {

        try {

            aramService.saveClose(aramRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
