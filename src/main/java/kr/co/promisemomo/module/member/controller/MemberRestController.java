package kr.co.promisemomo.module.member.controller;

import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.member.service.MemberService;
import kr.co.promisemomo.module.member.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberRestController {

    public final OauthService oauthService;

    public final MemberService memberService;

    //Redirect URI=http://localhost:8080/oauth/kakao
    // [ 테스트 코드 작성 ]
    // 잘못된 접근입니다. (코드 오류) - 코드 값 안적어서 보내보기
    // 로그인을 실패했습니다. (카카오 정보 오류) - 코드 아무값 막 적어서 보내보기
    // 로그인을 실패했습니다. (토큰 오류) - 있을 수 없는 경우은데 그래도 혹시 몰라서 실패처리를 추가한 경우임
    // 성공
    @GetMapping("/kakao")
    public HttpEntity<Object> kakaoCallback(@RequestParam String code) {

        if (code == null || code.replaceAll(" ", "").equals("")){
            return ResponseEntity.badRequest().body("잘못된 접근입니다. (코드 오류)");
        }

        String acces_token =oauthService.getKakaoAccessToken(code);
        if (acces_token.equals("fail")) {
            return ResponseEntity.badRequest().body("로그인을 실패했습니다. (카카오 정보 오류)");
        }

        // 추가적으로 로직 넣고
        Member member = memberService.createKakaoUser(acces_token);

        // 실패
        if (member == null) {
            return ResponseEntity.badRequest().body("로그인을 실패했습니다. (토큰 오류)");
        }

        // 성공
        return ResponseEntity.ok(member);
    }


}
