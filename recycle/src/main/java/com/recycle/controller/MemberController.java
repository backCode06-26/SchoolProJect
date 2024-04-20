package com.recycle.controller;

import com.recycle.domain.Member;
import com.recycle.domain.MemberDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @PostMapping(value = "members/signup")
    public String signUp(MemberDAO memberDAO) {
        // sha-256 암호화
        memberDAO.encryptPassword();
        Member member = new Member();
        member.setEmail(memberDAO.getEmail());

        return null;
    }
}
