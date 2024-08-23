package com.project.jjj_was.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
  private Integer userId;
    private String userLoginId;
    private String userName;
    private String userPhone;
    private String userEmail;
    private String userAddress;
    private String userAddressDetail;
    private String userGender;
    private Date userBirthdate;
    private String userSignUpDate;
}
