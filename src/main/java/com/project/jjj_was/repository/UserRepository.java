package com.project.jjj_was.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.jjj_was.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    // 특정 이메일이 존재하는지 확인합니다.
    boolean existsByUserEmail(String userEmail);

    // 특정 이메일을 기준으로 사용자를 찾습니다.
    Optional<UserEntity> findByUserEmail(String userEmail);

    // 특정 이메일과 이름으로 사용자를 찾습니다.
    Optional<UserEntity> findByUserEmailAndUserName(String userEmail, String userName);

    // 특정 사용자 ID와 이름, 이메일로 사용자를 찾습니다.
    Optional<UserEntity> findByUserIdAndUserNameAndUserEmail(Integer userId, String userName, String userEmail);

    // 특정 사용자 ID로 사용자를 찾습니다.
    Optional<UserEntity> findByUserId(Integer userId);

    // 특정 사용자 ID와 이메일이 존재하는지 확인합니다.
    boolean existsByUserIdAndUserEmail(Integer userId, String userEmail);

    // 특정 사용자 ID가 존재하는지 확인합니다.
    boolean existsById(Integer userId);

}
