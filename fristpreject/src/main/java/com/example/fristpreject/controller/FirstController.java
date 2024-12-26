package com.example.fristpreject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //
public class FirstController {

    // model은 보여주는 데이터이다.
    @GetMapping("/hi") // 경로을 설정하는 애노테이션입니다.
    public String hello(Model model) {
        model.addAttribute("username", "손오공");
        return "greetings";
    }

    @GetMapping("/bye")
    public String bye(Model model) {
        model.addAttribute("username", "XX");
        return "bye";
    }
}
