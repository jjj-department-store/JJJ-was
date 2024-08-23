package com.project.jjj_was.controller;

import com.project.jjj_was.common.constant.ApiMappingPattern;
import com.project.jjj_was.dto.ResponseDto;
import com.project.jjj_was.dto.request.auth.SignUpRequestDto;
import com.project.jjj_was.dto.request.auth.SignInRequestDto;
import com.project.jjj_was.dto.response.auth.SignUpPostResponseDto;
import com.project.jjj_was.dto.response.user.UserResponseDto;
import com.project.jjj_was.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiMappingPattern.AUTH)
public class UserController {

    @Autowired
    private AuthService authService;

    private static final String POST_SIGN_UP = "/signUp";
    private static final String POST_SIGN_IN = "/signIn";
    private static final String GET_USER_BY_ID = "/{userId}";
    private static final String PUT_UPDATE_USER = "/{userId}";
    private static final String DELETE_USER = "/{userId}";

    /**
     * 회원가입 처리 엔드포인트
     * @param requestBody 회원가입 요청 데이터
     * @return 회원가입 결과 응답
     */
    @PostMapping(POST_SIGN_UP)
    public ResponseDto<SignUpPostResponseDto> signUp(@Valid @RequestBody SignUpRequestDto requestBody) {
        return authService.signUp(requestBody);
    }

    /**
     * 로그인 처리 엔드포인트
     * @param loginRequestDto 로그인 요청 데이터
     * @return JWT 토큰
     */
    @PostMapping(POST_SIGN_IN)
    public ResponseDto<String> login(@Valid @RequestBody SignInRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    /**
     * 특정 사용자 ID로 사용자 정보를 조회하는 엔드포인트
     * @param userId 사용자 ID
     * @return 사용자 정보 응답
     */
    @GetMapping(GET_USER_BY_ID)
    public ResponseDto<UserResponseDto> getUserById(@PathVariable Integer userId) {
        return authService.findById(userId);
    }

    /**
     * 사용자 정보 업데이트 엔드포인트
     * @param userId 사용자 ID
     * @param signUpRequestDto 업데이트할 사용자 정보
     * @return 업데이트 결과 응답
     */
    @PutMapping(PUT_UPDATE_USER)
    public ResponseDto<Void> updateUserDetails(@PathVariable Integer userId, @Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        return authService.updateUserDetails(userId, signUpRequestDto);
    }

    /**
     * 사용자 삭제 엔드포인트
     * @param userId 사용자 ID
     * @return 삭제 결과 응답
     */
    @DeleteMapping(DELETE_USER)
    public ResponseDto<Void> deleteUser(@PathVariable Integer userId) {
        return authService.deleteUser(userId);
    }
}
