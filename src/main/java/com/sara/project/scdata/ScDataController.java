package com.sara.project.scdata;

import com.sara.project.scdata.dto.ScDataRequestDto;
import com.sara.project.scdata.dto.ScDataResponseDto;
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

@Api(tags = {"scData"})
@RestController
@RequestMapping("/scData")
@CrossOrigin
@Slf4j
public class ScDataController {

    @Autowired
    private ScDataService scDataService;

    @ApiOperation(value = "scData 조회")
    @GetMapping("")
    public ResponseEntity<ResponseMessage> getScDataList() {
        try {

            List<ScDataResponseDto> scDataResponseDtos
                    = scDataService.getScDataResponseDtoList();
            ResponseMessage responseMessage = new ResponseMessage(Const.success,scDataResponseDtos, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch(Exception e){
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(value = "scData 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getScDataOne(
            @PathVariable Long id
    ) {

        try {
            ScDataResponseDto groupResponseDto
                    = scDataService.getScDataResponseDto(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, groupResponseDto, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "ScData Save")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody ScDataRequestDto scDataRequestDto
    ) {
        log.info("scData save");
        try {

            scDataService.save(scDataRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "scData 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(
            @PathVariable Long id,
            @RequestBody ScDataRequestDto scDataRequestDto
    ) {

        try {
            scDataService.update(id, scDataRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "scData 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(
            @PathVariable Long id
    ) {

        try {
            scDataService.delete(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
