package com.recycle.repository;

import com.recycle.domain.Member;
import com.recycle.domain.MemberDAO;
import com.recycle.domain.UserDTO;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    // 사용자 정보 저장하기
    void save(Member member);
    // 사용자 이름, 가입 시간, 게임 점수을 출력함
    UserDTO getUserInfo(Long id);

    // email으로 조회하기
    Optional<Member> findEmail(String email);

    List<UserDTO> getAllUserInfo(int page, int size);

}
