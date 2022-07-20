package kr.co.promisemomo.module.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
    // 리뷰 테스트
    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

}
