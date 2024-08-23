package com.project.jjj_was.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    private Integer userId;

    @NotBlank
    private String userLoginId;

    @NotBlank
    private String userPassword;

    @NotBlank
    private String userPasswordCheck;

    @NotBlank
    private String userName;

    @NotBlank
    private String userPhone;

    @NotBlank
    private String userEmail;

    @NotBlank
    private String userAddress;

    @NotBlank
    private String userAddressDetail;

    private String userGender;

    private Date userBirthdate;
}
