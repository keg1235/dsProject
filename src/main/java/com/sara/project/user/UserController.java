package com.sara.project.user;


import com.sara.project.common.util.ErrResult;
import com.sara.project.user.dto.UserLoginRequestDto;
import com.sara.project.user.dto.UserLoginResponseDto;
import com.sara.project.user.dto.UserRequestDto;
import com.sara.project.user.dto.UserResponseDto;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Api(tags = {"User"})
@RestController
@RequestMapping("/user")
@CrossOrigin
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "전체 조회")
    @GetMapping("")
    public ResponseEntity<ResponseMessage> getUserList() {

        ResponseMessage responseMessage = null;
        try {
                List<UserResponseDto> userResponseDtoList
                        = userService.getUserResponseDtoList();
                responseMessage = new ResponseMessage(Const.success, userResponseDtoList, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch(Exception e) {
            responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "전체 조회")
    @GetMapping("/use")
    public ResponseEntity<ResponseMessage> getuseUserList() {

        ResponseMessage responseMessage = null;
        try {
            List<UserResponseDto> userResponseDtoList
                    = userService.getuseUserResponseDtoList();
            responseMessage = new ResponseMessage(Const.success, userResponseDtoList, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch(Exception e) {
            responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "User 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getSetupLabel(
            @PathVariable Long id
    ) {

        try {
            UserResponseDto userResponseDto
                    = userService.getUserResponseDto(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, userResponseDto, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "User 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> update(
            @PathVariable Long id,
            @RequestBody UserRequestDto userRequestDto
    ) {

        try {
            ErrResult  errResult=  userService.update(id, userRequestDto);
            log.info("{}",errResult.getErrYn());

            ResponseMessage responseMessage = new ResponseMessage(Const.success, errResult.getErrYn(),  errResult.getErrResult());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);

        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "login")
    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(
            @RequestBody UserLoginRequestDto userLoginRequestDto,
            HttpServletRequest request
    ) {
        UserLoginResponseDto userLoginResponseDto;
        try {

            boolean loginFlg = userService.verifyUser(userLoginRequestDto);
            log.info("{}",loginFlg);
            if (loginFlg){
                userLoginResponseDto = new UserLoginResponseDto(userService.getUserResponseEmailDto(userLoginRequestDto.getEmail()));
                HttpSession httpSession = request.getSession(true);
                // "USER"로 sessionVO를 세션에 바인딩한다.
                httpSession.setAttribute("USER", userLoginResponseDto);
            }else{
                userLoginResponseDto =  new UserLoginResponseDto(false);
            }

            ResponseMessage responseMessage = new ResponseMessage(Const.success, userLoginResponseDto, "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            log.info("{}",e);
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(value = "User 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(
            @PathVariable Long id
    ) {
        try {
            userService.delete(id);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(value = "User 신규 저장")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> save(
            @RequestBody UserRequestDto userRequestDto
    ) {
        try {
            ErrResult errResult = userService.save(userRequestDto);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, errResult.getErrYn(),  errResult.getErrResult());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "User 부분 삭제")
    @DeleteMapping("/list")
    public ResponseEntity<ResponseMessage> deleteByIdList(
            @RequestBody List<Long> idList
    ) {
        try {
            userService.deleteByIdList(idList);
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", e.toString());
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
