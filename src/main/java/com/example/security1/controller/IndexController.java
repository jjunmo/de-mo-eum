package com.example.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    //localhost3306
    //localhost3306/
    @GetMapping({"","/"})
    public String index(){
        //mustache 기본폴더 src/main/resources/
        //ViewResolver : templates (prefix) , .mustache (suffix) 생략 가능
        return "index"; // 경로 : src/main/resources/templates/index.mustache
    }
    @GetMapping("/user")
    public @ResponseBody String user(){
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "Mmnager";
    }

    //SpringSecurity가 중간에 낚아챔  - SecurityConfig 파일 생성 후 작동안함.
    @GetMapping("/login")
    public @ResponseBody String login(){
        return "login";
    }

    @GetMapping("/join")
    public @ResponseBody String join(){
        return "join";
    }

    @GetMapping("/joinProc")
    public @ResponseBody String joinProc(){
        return "회원가입 완료";
    }
}
