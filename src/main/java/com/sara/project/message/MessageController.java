package com.sara.project.message;

import com.sara.project.message.dto.MessageRequestDto;
import com.sara.project.message.dto.MessageResponseDto;
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

@Api(tags = {"Message"})
@RestController
@RequestMapping("/message")
@CrossOrigin
@Slf4j
public class MessageController {
    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "message 리스트 조회")
    @GetMapping("")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", defaultValue = "8"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string")
    })
    public ResponseEntity<ResponseMessage> getMessageList(Pageable pageable) {
        ResponseMessage responseMessage = null;
        try {
            if(pageable.getPageNumber() < 1) {
                List<MessageResponseDto> messageList
                        = messageService.getMessageList();
                responseMessage = new ResponseMessage(Const.success, messageList, "");
            } else {
                Pageable pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
                Page<MessageResponseDto> messageResponseDtoPage
                        = messageService.getMessagePage(pageRequest);
                responseMessage = new ResponseMessage(Const.success, messageResponseDtoPage, "");
            }
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch(Exception e) {
            responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "message 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> get(
            @PathVariable Long id
    ) {
        try {
            MessageResponseDto messageResponseDto = messageService.get(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, messageResponseDto, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "message 저장")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody MessageRequestDto messageRequestDto
    ) {
        try {
            messageService.save(messageRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "message 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(
            @PathVariable Long id,
            @RequestBody MessageRequestDto messageRequestDto
    ) {
        try {
            messageService.update(id, messageRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "message 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(
            @PathVariable Long id
    ) {
        try {
            messageService.delete(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
