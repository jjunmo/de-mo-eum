package kr.co.promisemomo.module.member.controller;

import kr.co.promisemomo.module.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;


    @GetMapping(value="/")
    public String index() {

        return "index.html";
    }

    @GetMapping(value="/login")
    public String login() {

        return "index.html";
    }

}
