package com.sara.project.qna;

import com.sara.project.qna.dto.QnaRequestDto;
import com.sara.project.qna.dto.QnaResponseDto;
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

@Api(tags = {"QNA"})
@RestController
@RequestMapping("/qna")
@CrossOrigin
@Slf4j
public class QnaController {
    @Autowired
    private QnaService qnaService;

    @ApiOperation(value = "qna 리스트 조회")
    @GetMapping("")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string")
    })
    public ResponseEntity<ResponseMessage> getQnaList(Pageable pageable) {
        ResponseMessage responseMessage = null;
        try {
            if(pageable.getPageNumber() < 1) {
                List<QnaResponseDto> qnaList
                        = qnaService.getQnaList();
                responseMessage = new ResponseMessage(Const.success, qnaList, "");
            } else {
                Pageable pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
                Page<QnaResponseDto> qnaResponseDtoPage
                        = qnaService.getQnaPage(pageRequest);
                responseMessage = new ResponseMessage(Const.success, qnaResponseDtoPage, "");
            }
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch(Exception e) {
            responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "qna 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> get(
            @PathVariable Long id
    ) {
        try {
            QnaResponseDto qnaResponseDto = qnaService.get(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, qnaResponseDto, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "qna 저장")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody QnaRequestDto qnaRequestDto
    ) {
        try {
            qnaService.save(qnaRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "qna 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(
            @PathVariable Long id,
            @RequestBody QnaRequestDto qnaRequestDto
    ) {
        try {
            qnaService.update(id, qnaRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "qna 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(
            @PathVariable Long id
    ) {
        try {
            qnaService.delete(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
