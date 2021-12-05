package com.sara.project.eletron;

import com.sara.project.ddc.dto.DdcResponseDto;
import com.sara.project.eletron.dto.*;
import com.sara.project.eletron.EletronService;
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


@Api(tags = {"Electron"})
@RestController
@RequestMapping("/electron")
@CrossOrigin
@Slf4j
public class EletronController {

    @Autowired
    private EletronService eletronService;


    @ApiOperation(value = "DDC 리스트 조회")
    @GetMapping("")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string")
    })
    public ResponseEntity<ResponseMessage> getEletronPage(Pageable pageable) {
        try {
            ResponseMessage responseMessage = null;
            if(pageable.getPageNumber() < 1) {
                List<EletronResponseDto> eletronResponseDtos
                        = eletronService.getEletronResponseDtoList();
                responseMessage = new ResponseMessage(Const.success, eletronResponseDtos, "");
            } else {
                Pageable pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
                Page<EletronResponseDto> eletronResponseDtospage
                        = eletronService.getEletronPage(pageRequest);
                responseMessage = new ResponseMessage(Const.success, eletronResponseDtospage, "");
            }
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "그래프 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getDdcOne(
            @PathVariable Long id
    ) {

        try {
            List<EletronMapper> eletronMappers
                    = eletronService.getEletronGraphList(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, eletronMappers, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "그래프 조회")
    @GetMapping("/excel/{id}")
    public ResponseEntity<ResponseMessage> getExcel(
            @PathVariable Long id
    ) {

        try {
            List<EletronMapper> eletronMappers
                    = eletronService.getExcelList(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, eletronMappers, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "엑셀 내역 조회")
    @GetMapping("/{id}/{std}/{end}")
    public ResponseEntity<ResponseMessage> getDdcOne(
            @PathVariable Long id,
            @PathVariable String std,
            @PathVariable String end
    ) {

        try {
            List<EletronExcelMapper> eletronMappers
                    = eletronService.getEletronExcelList(id,std,end);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, eletronMappers, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(value = "Eletron Save")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody EletronRequestDto eletronRequestDto
    ) {
        log.info("eletron save");
        try {

            eletronService.save(eletronRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Eletron 삭제")
    @DeleteMapping("")
    public ResponseEntity<ResponseMessage> delete(

    ) {

        try {
            eletronService.deleteAll();
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
