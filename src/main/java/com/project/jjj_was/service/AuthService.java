package com.project.jjj_was.service;

import com.project.jjj_was.dto.ResponseDto;
import com.project.jjj_was.dto.request.auth.SignUpRequestDto;
import com.project.jjj_was.dto.request.auth.SignInRequestDto;
import com.project.jjj_was.dto.response.auth.SignUpPostResponseDto;
import com.project.jjj_was.dto.response.user.UserResponseDto;
import com.project.jjj_was.entity.UserEntity;
import com.project.jjj_was.repository.UserRepository;
import com.project.jjj_was.security.TokenProvider;
import com.project.jjj_was.common.constant.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 회원가입 처리 메서드
     * 사용자 입력 데이터를 받아 데이터베이스에 저장합니다.
     * 비밀번호는 암호화하여 저장됩니다.
     */
    public ResponseDto<SignUpPostResponseDto> signUp(SignUpRequestDto dto) {

        // 이미 존재하는 사용자 ID인지 확인
        if (userRepository.existsById(dto.getUserId())) {
            return ResponseDto.setFailed(ResponseMessage.EXIST_DATA);
        }

        // 이미 존재하는 이메일인지 확인
        if (userRepository.existsByUserEmail(dto.getUserEmail())) {
            return ResponseDto.setFailed(ResponseMessage.EXIST_DATA);
        }

        // 비밀번호와 비밀번호 확인 일치 여부 확인
        if (!dto.getUserPassword().equals(dto.getUserPasswordCheck())) {
            return ResponseDto.setFailed(ResponseMessage.NOT_MATCH_PASSWORD);
        }

        // 비밀번호 암호화 처리
        dto.setUserPassword(passwordEncoder.encode(dto.getUserPassword()));

        // 사용자 엔티티 생성 및 데이터베이스에 저장
        UserEntity userEntity = new UserEntity(dto);
        try {
            userRepository.save(userEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }

        // 응답 데이터 생성
        SignUpPostResponseDto data = new SignUpPostResponseDto(userEntity);
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    /**
     * 로그인 처리 메서드
     * 사용자 입력 데이터를 바탕으로 사용자 인증을 수행합니다.
     * 인증이 성공하면 JWT 토큰을 반환합니다.
     */
    public ResponseDto<String> login(SignInRequestDto SignInRequestDto) {
        // 사용자 로그인 ID로 사용자 조회
        Optional<UserEntity> userEntityOptional = userRepository.findByUserLoginId(SignInRequestDto.getUserLoginId());

        // 사용자가 존재하고 비밀번호가 일치하는지 확인
        if (userEntityOptional.isPresent() &&
            passwordEncoder.matches(SignInRequestDto.getUserPassword(), userEntityOptional.get().getUserPassword())) {

            // JWT 토큰 생성 및 반환
            String token = tokenProvider.create(SignInRequestDto.getUserLoginId());
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, token);
        } else {
            return ResponseDto.setFailed(ResponseMessage.INVALID_CREDENTIALS);
        }
    }

    /**
     * 특정 사용자 ID로 사용자 정보를 조회하는 메서드
     */
    public ResponseDto<UserResponseDto> findById(Integer userId) {
        // 사용자 ID로 사용자 조회
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            UserResponseDto responseDto = new UserResponseDto(
                userEntity.getUserId(),
                userEntity.getUserLoginId(),
                userEntity.getUserName(),
                userEntity.getUserPhone(),
                userEntity.getUserEmail(),
                userEntity.getUserAddress(),
                userEntity.getUserAddressDetail(),
                userEntity.getUserGender(),
                userEntity.getUserBirthdate(),
                userEntity.getUserSignUpDate()
            );
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, responseDto);
        } else {
            return ResponseDto.setFailed(ResponseMessage.USER_NOT_FOUND);
        }
    }

    /**
     * 사용자 정보를 업데이트하는 메서드
     * 사용자 ID로 기존 사용자를 조회하고, 새로운 정보로 업데이트합니다.
     */
    public ResponseDto<Void> updateUserDetails(Integer userId, SignUpRequestDto signUpRequestDto) {
        // 사용자 ID로 사용자 조회
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            // 비밀번호 암호화
            if (signUpRequestDto.getUserPassword() != null) {
                signUpRequestDto.setUserPassword(passwordEncoder.encode(signUpRequestDto.getUserPassword()));
            }

            // 사용자 정보 업데이트
            userEntity.updateUserDetails(signUpRequestDto);
            userRepository.save(userEntity);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
        } else {
            return ResponseDto.setFailed(ResponseMessage.USER_NOT_FOUND);
        }
    }

    /**
     * 사용자 삭제 메서드
     * 사용자 ID로 사용자를 삭제합니다.
     */
    public ResponseDto<Void> deleteUser(Integer userId) {
        // 사용자 ID로 사용자 존재 여부 확인
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
        } else {
            return ResponseDto.setFailed(ResponseMessage.USER_NOT_FOUND);
        }
    }
}
