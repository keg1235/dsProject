package com.sara.project.gateway;

import com.sara.project.gateway.dto.GatewayRequestDto;
import com.sara.project.gateway.dto.GatewayResponseDto;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"GateWay"})
@RestController
@RequestMapping("/gateway")
@CrossOrigin
@Slf4j
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @ApiOperation(value = "GateWay 조회")
    @GetMapping("")
    public ResponseEntity<ResponseMessage> getGatewayList() {
        try {

                List<GatewayResponseDto> gatewayResponseDtos
                        = gatewayService.getGatewayResponseDtoList();
                ResponseMessage responseMessage = new ResponseMessage(Const.success,gatewayResponseDtos, "");
                return new ResponseEntity<>(responseMessage, HttpStatus.OK);
            } catch(Exception e){
                ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
                return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }



    @ApiOperation(value = "GateWay 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getGatewayOne(
            @PathVariable Long id
    ) {

        try {
            GatewayResponseDto groupResponseDto
                    = gatewayService.getGatewayResponseDto(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, groupResponseDto, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Gateway Save")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody GatewayRequestDto gatewayRequestDto
    ) {
        log.info("gateway save");
        try {

            gatewayService.save(gatewayRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "GateWay 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(
            @PathVariable Long id,
            @RequestBody GatewayRequestDto gatewayRequestDto
    ) {

        try {
            gatewayService.update(id, gatewayRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "GateWay 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(
            @PathVariable Long id
    ) {

        try {
            gatewayService.delete(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
