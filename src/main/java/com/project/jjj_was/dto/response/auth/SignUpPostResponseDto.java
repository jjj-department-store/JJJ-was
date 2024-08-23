package com.project.jjj_was.dto.response.auth;

import com.project.jjj_was.entity.UserEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpPostResponseDto {

    UserEntity userEntity;

    public SignUpPostResponseDto(UserEntity userEntity) {
	
        this.userEntity = userEntity;
        
    }
}
