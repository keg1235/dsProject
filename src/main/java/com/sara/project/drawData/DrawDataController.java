package com.sara.project.drawData;

import com.sara.project.drawData.dto.*;
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

@Api(tags = {"drawData"})
@RestController
@RequestMapping("/drawData")
@CrossOrigin
@Slf4j
public class DrawDataController {


    @Autowired
    private DrawDataService drawDataService;

    @ApiOperation(value = "특정 DCP 조회")
    @GetMapping("")
    public ResponseEntity<ResponseMessage> getDrawAll(
    ) {

        try {
            List<DrawDataResponseDto> drawDataResponseDtos
                    = drawDataService.getDrawAll();
            ResponseMessage responseMessage = new ResponseMessage(Const.success, drawDataResponseDtos, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(value = "특정 DCP 조회")
    @GetMapping("/group")
    public ResponseEntity<ResponseMessage> getDrawgroupAll(
    ) {

        try {
            List<DrawDataResponseDto> drawDataResponseDtos
                    = drawDataService.getDrawAll();
            ResponseMessage responseMessage = new ResponseMessage(Const.success, drawDataResponseDtos, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "특정 DCP 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getdrawOne(
            @PathVariable Long id,
            @RequestParam Boolean type
    ) {

        try {

                DrawAllResponseDto drawDataResponseDtos
                        = drawDataService.getDrawList(id,type);


            ResponseMessage responseMessage = new ResponseMessage(Const.success, drawDataResponseDtos, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "DCP 저장")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody DrawAllRequestDto drawAllRequestDtos
    ) {

        try {
            drawDataService.save(drawAllRequestDtos);

            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "특정 DCP 조회")
    @GetMapping("/drawGroup")
    public ResponseEntity<ResponseMessage> getDrawGroupAll(
    ) {

        try {
            List<DrawGroupResponseDto> drawDataResponseDtos
                    = drawDataService.getDrawGroupAll();
            ResponseMessage responseMessage = new ResponseMessage(Const.success, drawDataResponseDtos, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "특정 DCP 조회")
    @GetMapping("/drawGroup/{id}")
    public ResponseEntity<ResponseMessage> getdrawOne(
            @PathVariable Long id
    ) {

        try {

            DrawGroupResponseDto drawGroup
                    = drawDataService.getDrawGroup(id);


            ResponseMessage responseMessage = new ResponseMessage(Const.success, drawGroup, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "특정 DCP 저장")
    @PostMapping("/drawGroup")
    public ResponseEntity<ResponseMessage> getDrawGroupSave(
            @RequestBody DrawGroupRequestDto drawDataResponseDtos
    ) {

        try {
            drawDataService.dataGroupSave(drawDataResponseDtos);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, drawDataResponseDtos, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "특정 DCP 저장")
    @PutMapping("/drawGroup")
    public ResponseEntity<ResponseMessage> getDrawGroupUpdate(
            @RequestBody DrawGroupRequestDto drawDataResponseDtos
    ) {

        try {
            drawDataService.dataGroupUpdate(drawDataResponseDtos);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, drawDataResponseDtos, "");
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

            drawDataService.delete(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "DCP 삭제")
    @DeleteMapping("/drawGroup/{id}")
    public ResponseEntity<ResponseMessage> dataGroupdelete(
            @PathVariable Long id
    ) {

        try {

            drawDataService.dataGroupDelete(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
