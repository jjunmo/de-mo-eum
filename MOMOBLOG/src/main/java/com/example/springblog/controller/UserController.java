package com.example.springblog.controller;

import com.example.springblog.config.auth.PrincipalDetail;
import com.example.springblog.model.dto.KakaoProfile;
import com.example.springblog.model.dto.OAuthToken;
import com.example.springblog.model.entity.User;
import com.example.springblog.service.BoardService;
import com.example.springblog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;


// 인증이 안된 사용자들이 출입할 수 있는 경로
// /auth/** 허용
// 그리고 그냥 주소가 / 이면 index.html 허용
// static 이하에 있는 /js/** , /css/**, /image/** 허용

@Controller
@RequiredArgsConstructor
public class UserController {

    // Application.yml
    @Value("${root.key}")
    private String rootKey;

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final BoardService boardService;


    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }


    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }


    // 카카오
    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) {
        // @ResponseBody는 Data를 리턴해주는 컨트롤러 함수가 됨


        // POST방식으로 Key=Value 데이터를 요청 (카카오쪽으로)
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "08c37023b1f6eec0e156e4d5c26bc9b6");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청하기 - POST방식으로 - 그리고 response 변수의 응답 받음
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (oAuthToken != null){
            // POST방식으로 Key=Value 데이터를 요청 (카카오쪽으로)
            rt = new RestTemplate();

            // HttpHeader 오브젝트 생성
            headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


            // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
            HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                    new HttpEntity<>(headers);

            // Http 요청하기 - POST방식으로 - 그리고 response 변수의 응답 받음
            response = rt.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.POST,
                    kakaoProfileRequest,
                    String.class
            );

            System.out.println(response.getBody());


            // Gson, Json Simple, ObjectMapper
            objectMapper = new ObjectMapper();
            KakaoProfile kakaoProfile = null;

            try {
                kakaoProfile = objectMapper.readValue(response.getBody(), KakaoProfile.class);

                // User 오브젝트 : username, password, email
                System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
                System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
                System.out.println("블로그서버 유저네임 : " + "KAKAO_" + kakaoProfile.properties.getNickname());
                System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
                System.out.println("블로그서버 패스워드 : " + rootKey);

                User kakaoUser = User.builder()
                        .username("KAKAO_" + kakaoProfile.properties.getNickname())
                        .password(rootKey)
                        .email(kakaoProfile.getKakao_account().getEmail())
                        .oauth("kakao")
                        .build();


                // 가입자 혹은 비가입자 체크해서 처리
                User originUser = userService.회원찾기(kakaoUser.getUsername());
                if (originUser.getUsername() == null) {
                    System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다.");
                    userService.회원가입(kakaoUser);
                }

                System.out.println("자동 로그인을 진행합니다.");

                // 로그인 처리 및 세션처리
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), rootKey));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            // TODO 확인 필요
            return "user/loginForm";
        }


        return "redirect:/";
    }


    @GetMapping("/user/updateForm")
    public String updateForm(Model model, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        model.addAttribute("user", principalDetail.getUser());
        return "/user/updateForm";
    }


    // 작성글 보기
    @GetMapping("/user/myBoard")
    public String index(Model model, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        model.addAttribute("user", principalDetail.getUser());
        model.addAttribute("boards", boardService.작성글목록(principalDetail.getUser()));
        return "/user/myBoard";
    }

    @GetMapping("/auth/kakao/logout")
    public String logout(){
        return "/";
    }
}
