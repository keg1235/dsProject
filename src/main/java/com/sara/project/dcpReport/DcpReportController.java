package com.sara.project.dcpReport;

import com.sara.project.dcpReport.dto.DcpReportRequestDto;
import com.sara.project.dcpReport.dto.DcpReportResponseDto;
import com.sara.project.eletron.EletronService;
import com.sara.project.eletron.dto.EletronExcelMapper;
import com.sara.project.eletron.dto.EletronMapper;
import com.sara.project.eletron.dto.EletronRequestDto;
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


@Api(tags = {"DCPREPORT"})
@RestController
@RequestMapping("/dcpReport")
@CrossOrigin
@Slf4j
public class DcpReportController {


    @Autowired
    private DcpReportService dcpReportService;


    @ApiOperation(value = "DcpReport 리스트 조회")
    @GetMapping("")
    public ResponseEntity<ResponseMessage> getEletronPage() {
        try {
            ResponseMessage responseMessage = null;

            List<DcpReportResponseDto> dcpReportResponseDtos
                    = dcpReportService.getdcpReportResponseDtoList();
            responseMessage = new ResponseMessage(Const.success, dcpReportResponseDtos, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "DcpReport 기간 리스트 조회")
    @GetMapping("/{std}/{end}")
    public ResponseEntity<ResponseMessage> getDdcOne(
            @PathVariable String std,
            @PathVariable String end
    ) {

        try {
            List<DcpReportResponseDto> dcpReportResponseDtos
                    = dcpReportService.getReportExcelList(std,end);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, dcpReportResponseDtos, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(value = "DcpReport Sample")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody DcpReportRequestDto dcpReportRequestDto
    ) {
        log.info("eletron save");
        try {

            dcpReportService.save(dcpReportRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "DcpReport 삭제")
    @DeleteMapping("")
    public ResponseEntity<ResponseMessage> delete(

    ) {

        try {
            dcpReportService.deleteAll();
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
