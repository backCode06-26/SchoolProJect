package com.recycle.service;

import com.recycle.domain.Member;
import com.recycle.domain.MemberDAO;
import com.recycle.domain.UserDTO;
import com.recycle.repository.DataMemberRepository;

import java.util.List;

public class MemberService {

    private final DataMemberRepository dataMemberRepository;

    public MemberService(DataMemberRepository dataMemberRepository) {
        this.dataMemberRepository = dataMemberRepository;
    }

    // 저장하기
    public void join(Member member) {
        isDuplicateMember(member);
        dataMemberRepository.save(member);
    }

    // 이미 만들어진 계정이 존재하는지 유효성 검사
    private void isDuplicateMember(Member member) {
        dataMemberRepository.findEmail(member.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재한 회원입니다.");
                });
    }

    // 유저 정보 조회
    public UserDTO getUserDAO(Long id) {
        // 특정 id에 해당하는 유저 정보를 조회하여 반환하는 로직을 구현하세요.
        return dataMemberRepository.getUserInfo(id);
    }

    // 유저 점수 리스트
    public List<UserDTO> getUserDAOList(int page, int size) {
        // 유저의 점수 리스트를 조회하여 반환하는 로직을 구현하세요.
        return dataMemberRepository.getAllUserInfo(page, size);
    }
}
