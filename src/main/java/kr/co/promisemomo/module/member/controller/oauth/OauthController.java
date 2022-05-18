package kr.co.promisemomo.module.member.controller.oauth;

import kr.co.promisemomo.module.member.service.oauth.OauthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class OauthController {

    @Autowired
    public OauthService oauthService;

    //Redirect URI=http://localhost:8080/oauth/kakao
    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) {
        System.out.println("code :" +code);
        String acces_token =oauthService.getKakaoAccessToken(code);
    }
}
