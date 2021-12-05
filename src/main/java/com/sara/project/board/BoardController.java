package com.sara.project.board;

import com.sara.project.board.dto.BoardRequestDto;
import com.sara.project.board.dto.BoardResponseDto;
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

@Api(tags = {"Board"})
@RestController
@RequestMapping("/board")
@CrossOrigin
@Slf4j
public class BoardController {

    @Autowired
    private BoardService boardService;

    @ApiOperation(value = "board 리스트 조회")
    @GetMapping("")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string")
    })
    public ResponseEntity<ResponseMessage> getBoardList(Pageable pageable) {
        ResponseMessage responseMessage = null;
        try {
            if(pageable.getPageNumber() < 1) {
                List<BoardResponseDto> boardList
                        = boardService.getBoardList();
                responseMessage = new ResponseMessage(Const.success, boardList, "");
            } else {
                Pageable pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
                Page<BoardResponseDto> boardResponseDtoPage
                        = boardService.getBoardPage(pageRequest);
                responseMessage = new ResponseMessage(Const.success, boardResponseDtoPage, "");
            }
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch(Exception e) {
            responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "board 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> get(
            @PathVariable Long id
    ) {
        try {
            BoardResponseDto boardResponseDto = boardService.get(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, boardResponseDto, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "board 저장")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        try {
            boardService.save(boardRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "board 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(
            @PathVariable Long id,
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        try {
            boardService.update(id, boardRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "board 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(
            @PathVariable Long id
    ) {
        try {
            boardService.delete(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
