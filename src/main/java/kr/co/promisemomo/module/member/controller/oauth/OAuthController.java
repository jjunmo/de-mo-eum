package kr.co.promisemomo.module.member.controller.oauth;

import kr.co.promisemomo.module.member.service.OauthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    /**
     * 카카오 callback
     * [GET] /oauth/kakao/callback
     */

    @Autowired
    public OauthService oauthService;

    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) {
        System.out.println(code);
        String acces_token=oauthService.getKakaoAccessToken(code);
        System.out.println(acces_token);
        oauthService.createKakaoUser(acces_token);
        oauthService.kakaoLogout(acces_token);
    }

}
