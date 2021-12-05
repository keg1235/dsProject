package com.sara.project.user;

import com.sara.project.common.util.ErrResult;
import com.sara.project.user.dto.UserLoginRequestDto;
import com.sara.project.user.dto.UserRequestDto;
import com.sara.project.user.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponseDto getUserResponseDto(Long id);

    ErrResult save(UserRequestDto gatewayRequestDto);

    List<UserResponseDto> getUserResponseDtoList();

    Page<UserResponseDto> getUserPage(Pageable pageRequest);

    ErrResult update(Long id, UserRequestDto UserRequestDto);

    void delete(Long id);

    String EmailCheck(String email);

    boolean verifyUser(UserLoginRequestDto userLoginRequestDto);

    UserResponseDto getUserResponseEmailDto(String email);

    void deleteByIdList(List<Long> idList);

    Boolean loginCheck(String email, String password);

    List<UserResponseDto> getuseUserResponseDtoList();
}
