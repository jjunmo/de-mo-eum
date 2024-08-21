package kr.co.promisemomo.module.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MemberController {
    // 리뷰 테스트
    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("hello","서버값");
        return "/index";
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

}
