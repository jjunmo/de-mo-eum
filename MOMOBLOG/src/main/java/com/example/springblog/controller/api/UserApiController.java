package com.example.springblog.controller.api;

import com.example.springblog.model.entity.User;
import com.example.springblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;


    // Json 데이터를 받으려면 @RequestBody로 받아야함
    // 회원가입
    @PostMapping("/auth/joinProc")
    public ResponseEntity<Object> save(@RequestBody User user) {
        System.out.println("UserApiController : save 호출됨");
        userService.회원가입(user);
        return ResponseEntity.ok().build(); // 자바 오브젝트를 JSON으로 변환하여 전송 (JACKSON)
    }

    @PutMapping("/user")
    public ResponseEntity<Object>  update(@RequestBody User user) {

        userService.회원수정(user);
        // 여기서는 트랜잭션이 종료되기 때문에 DB값은 변경이 됐음
        // 하지만 세션값은 변경되지 않은 상태이기 때문에 직접 세션 값을 변경해줘야함.
        // 한마디로 DB는 회원수정이 이뤄졌지만, 실제 웹에서는 세션정보는 DB수정 전 세션이라는 뜻
        // 해결하기 위해서 세션 정보를 직접 변경 해줘야함

        // 세션등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok().build();
    }


}
