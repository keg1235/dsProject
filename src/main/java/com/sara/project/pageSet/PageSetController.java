package com.sara.project.pageSet;

import com.sara.project.eletron.dto.EletronMapper;
import com.sara.project.eletron.dto.EletronRequestDto;
import com.sara.project.pageSet.dto.PageSetRequest;
import com.sara.project.pageSet.dto.PageSetResponse;
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


@Api(tags = {"pageSet"})
@RestController
@RequestMapping("/pageSet")
@CrossOrigin
@Slf4j
public class PageSetController
{
    @Autowired
    private PageSetService pageSetService;

    @ApiOperation(value = "설정 조회")
    @GetMapping("")
    public ResponseEntity<ResponseMessage> getSetting(
    ) {

        try {
            PageSetResponse pageSetResponse = pageSetService.get();
            ResponseMessage responseMessage = new ResponseMessage(Const.success, pageSetResponse, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "설정 저장")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody PageSetRequest pageSetRequest
    ) {
        try {
            pageSetService.save(pageSetRequest);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
