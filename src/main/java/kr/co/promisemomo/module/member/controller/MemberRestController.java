package kr.co.promisemomo.module.member.controller;

import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.member.service.MemberService;
import kr.co.promisemomo.module.member.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberRestController {

    public final OauthService oauthService;

    public final MemberService memberService;

    //kauth.kakao.com/oauth/authorize?client_id=f398d5912c151f13e22b5fecfbd1f249&redirect_uri=http://localhost:8080/oauth/kakao&response_type=code
    //Redirect URI=http://localhost:8080/oauth/kakao

    // TODO 테스트 코드 실행시 Required request parameter
    //  'code' for method parameter type String is not present 이라는 스프링 자체 오류가 나기
    //  때문에 @RequestParam(defaultValue = "") 추가해줌

    @GetMapping("/oauth/kakao")
    public HttpEntity<Object> kakaoCallback(@RequestParam(defaultValue = "") String code) {

        if (code == null || code.replaceAll(" ", "").equals("")){
            return ResponseEntity.badRequest().body("잘못된 접근입니다. (코드 오류)");
        }

        String acces_token =oauthService.getKakaoAccessToken(code);
            if (acces_token.equals("fail")) {
                return ResponseEntity.badRequest().body("토큰을 받아오는데 오류가 발생");
        }

        // 추가적으로 로직 넣고
        Member member = memberService.createMember(acces_token);

        // 실패
        if (member == null) {
            return ResponseEntity.badRequest().body("로그인을 실패했습니다. (토큰 오류)");
        }

        // 성공
        return ResponseEntity.ok(member);
    }

    @PutMapping("/member/{id}")
    public HttpEntity<Object> updateMember(@PathVariable("id") Long id , @RequestBody Member member){
        memberService.updateMember(id, member);
        return ResponseEntity.ok(member);
    }



}
