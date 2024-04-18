package com.recycle.controller;

import com.recycle.domain.Member;
import com.recycle.domain.MemberDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping(value = "members/signup")
    public String signUp(MemberDAO memberDAO) {
        Member member = new Member();
        member.setEmail(memberDAO.getEmail());
        memberDAO.encryptPassword();

        return "redirect:";
    }
}
