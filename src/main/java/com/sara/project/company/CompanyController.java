package com.sara.project.company;

import com.sara.project.company.dto.CompanyRequestDto;
import com.sara.project.company.dto.CompanyResponseDto;
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

@Api(tags = {"Company"})
@RestController
@RequestMapping("/company")
@CrossOrigin
@Slf4j
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @ApiOperation(value = "company 리스트 조회")
    @GetMapping("")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string")
    })
    public ResponseEntity<ResponseMessage> getCompanyList(Pageable pageable) {
        ResponseMessage responseMessage = null;
        try {
            if(pageable.getPageNumber() < 1) {
                List<CompanyResponseDto> companyList
                        = companyService.getCompanyList();
                responseMessage = new ResponseMessage(Const.success, companyList, "");
            } else {
                Pageable pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
                Page<CompanyResponseDto> companyResponseDtoPage
                        = companyService.getCompanyPage(pageRequest);
                responseMessage = new ResponseMessage(Const.success, companyResponseDtoPage, "");
            }
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch(Exception e) {
            responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "company 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> get(
            @PathVariable Long id
    ) {
        try {
            CompanyResponseDto companyResponseDto = companyService.get(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, companyResponseDto, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "company 저장")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody CompanyRequestDto companyRequestDto
    ) {
        try {
            companyService.save(companyRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "company 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(
            @PathVariable Long id,
            @RequestBody CompanyRequestDto companyRequestDto
    ) {
        try {
            companyService.update(id, companyRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "company 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(
            @PathVariable Long id
    ) {
        try {
            companyService.delete(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
