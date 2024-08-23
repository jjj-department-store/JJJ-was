package com.project.jjj_was.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.jjj_was.dto.request.auth.SignUpRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity(name = "users")
public class UserEntity {

	// 필수 필드
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId; // 고유 식별자

	@Column(nullable = false, length = 50)
	private String userLoginId;

	@Column(nullable = false, length = 100)
	private String userPassword;

	@Column(nullable = false, length = 100)
	private String userName;

	@Column(nullable = false, length = 15)
	private String userPhone;

	@Column(nullable = false, length = 100)
	private String userEmail;

	@Column(nullable = false, length = 255)
	private String userAddress;

	@Column(nullable = false, length = 255)
	private String userAddressDetail;

	// 선택 필드
	private String userGender; // 성별
	private Date userBirthdate; // 생년월일

	// 날짜 필드
	private String userSignUpDate; // 가입 날짜
	private Date userWithdrawDate; // 탈퇴 날짜

	public UserEntity(SignUpRequestDto dto) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		this.userId = dto.getUserId();
		this.userLoginId = dto.getUserLoginId();
		this.userPassword = dto.getUserPassword();
		this.userName = dto.getUserName();
		this.userPhone = dto.getUserPhone();
		this.userEmail = dto.getUserEmail();
		this.userAddress = dto.getUserAddress();
		this.userAddressDetail = dto.getUserAddressDetail();
		this.userGender = dto.getUserGender();
		this.userBirthdate = dto.getUserBirthdate();
		this.userSignUpDate = dateFormat.format(new Date());
		this.userWithdrawDate = null; // 기본값은 null
	}

	public void updateUserDetails(SignUpRequestDto dto) {
        this.userName = dto.getUserName(); // 이름 업데이트
        this.userPhone = dto.getUserPhone(); // 휴대폰 번호 업데이트
        this.userEmail = dto.getUserEmail(); // 이메일 업데이트
        this.userAddress = dto.getUserAddress(); // 주소 업데이트
        this.userAddressDetail = dto.getUserAddressDetail(); // 상세 주소 업데이트
        this.userGender = dto.getUserGender(); // 성별 업데이트
        this.userBirthdate = dto.getUserBirthdate(); // 생년월일 업데이트
    }
    

}
