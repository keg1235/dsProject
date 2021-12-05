package com.sara.project.user;

import com.sara.project.common.Role;
import com.sara.project.common.util.ErrResult;
import com.sara.project.user.dto.UserLoginRequestDto;
import com.sara.project.user.dto.UserRequestDto;
import com.sara.project.user.dto.UserResponseDto;
import com.sara.project.util.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResponseDto> getUserResponseDtoList() {
        List<User> userlist = userRepository.findAll();
        List<UserResponseDto> UserResponseDtos
                = userlist.stream().map(UserResponseDto::of)
                .collect(Collectors.toList());
        return UserResponseDtos;
    }


    @Override
    public List<UserResponseDto> getuseUserResponseDtoList() {
        List<User> userlist = userRepository.findByIdUseYn("YES");
        List<UserResponseDto> UserResponseDtos
                = userlist.stream().map(UserResponseDto::of)
                .collect(Collectors.toList());
        return UserResponseDtos;
    }

    @Override
    public UserResponseDto getUserResponseDto(Long id) {
        Optional<User> user = userRepository.findById(id);
        UserResponseDto userResponseDto =
                UserResponseDto.of(user.get());

        return userResponseDto;
    }

    @Override
    public Page<UserResponseDto> getUserPage(Pageable pageRequest) {
        Page<User> users = userRepository.findAll(pageRequest);

        Page<UserResponseDto> userResponseDtoPage = users.map(
                user -> UserResponseDto.of(user)
        );
        return userResponseDtoPage;
    }

    @Override
    public ErrResult save(UserRequestDto userRequestDto) {
        String errCheckYn ="저장완료";
        boolean errYn =true;
        try {
            if(!userRequestDto.getPassword().equals(userRequestDto.getPasswordYn())){
                errCheckYn ="비밀번호를 확인해주세요"; //비번체크
                errYn =false;

            }
            List<User> users = userRepository.findAll();
            int emailCount  = 0 ;
            int phoneCount  = 0 ;
            for(User user : users) {
                if (user.getEmail().equals(userRequestDto.getEmail())) {
                    emailCount++;
                }
            }
            String message ="가입가능한 Email 입니다.";
            if (emailCount > 0){
                errCheckYn ="이미 가입되어 있는 Email 입니다.";
                errYn =false;
            }
            if(errYn){
                Long generatedId = userRepository.findMaxValue() + 1;
                User user = userRequestDto.of(generatedId);
                userRepository.save(user);
            }

        } catch (Exception e) {
            throw new ServiceException("UserService", "save", e.toString());
        }


        return new ErrResult(errYn,errCheckYn);
    }

    @Override
    public String EmailCheck(String email) {
        List<User> users = userRepository.findAll();
        int emailCount  = 0 ;
        int phoneCount  = 0 ;
            for(User user : users) {
                if (user.getEmail().equals(email)) {
                    emailCount++;
                }
            }
            String message ="가입가능한 Email 입니다.";
            if (emailCount > 0){
                message ="이미 가입되어 있는 Email 입니다.";
            }
        return message;
    }
    @Override
    public boolean verifyUser(UserLoginRequestDto userLoginRequestDto) {
        User user = userRepository.findByEmailAndPassword(userLoginRequestDto.getEmail(), userLoginRequestDto.getUserPwd());
        System.out.println(user.getEmail());
        return user.getEmail().equals(null) ? false : true;
    }

    @Override
    public UserResponseDto getUserResponseEmailDto(String email) {
        User user = userRepository.findByEmail(email);
        UserResponseDto userResponseDto =
                UserResponseDto.of(user);
        return userResponseDto;
    }

    @Override
    public ErrResult update(Long id, UserRequestDto userRequestDto) {
        String errCheckYn ="저장완료";
        boolean errYn =true;
        try {
            if(!userRequestDto.getPassword().equals(userRequestDto.getPasswordYn())){
                errCheckYn ="비밀번호를 확인해주세요"; //비번체크
                errYn =false;

            }
            if(errYn){
                User User = userRequestDto.of(id);
                userRepository.save(User);
            }
        } catch (Exception e) {
            throw new ServiceException("UserService", "update", e.toString());
        }
        //ErrResult errResult =  new ErrResult(true,"저장완료");
        return  new ErrResult(errYn,errCheckYn);
    }

    @Override
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("UserService", "delete", e.toString());
        }
    }

    @Transactional
    @Override
    public void deleteByIdList(List<Long> idList) {
        try {
            userRepository.deleteByIdIn(idList);
        } catch (Exception e) {
            throw new ServiceException("UserService", "deleteByIdList", e.toString());
        }
    }

    @Override
    public Boolean loginCheck(String email, String password) {
        long count = userRepository.countByEmailAndPassword(email,password);
        boolean result = false;
        if (count > 0) { // true일 경우 세션에 등록

            result = true;
        }


        return result;

    }
}
