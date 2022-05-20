package kr.co.promisemomo.module.member.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import kr.co.promisemomo.module.member.entity.KakaoProfile;
import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.member.repository.KakaoProfileRepository;
import kr.co.promisemomo.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    // TODO: 이것은 잘못된 코드
    private final MemberRepository memberRepository;
    private final KakaoProfileRepository kakaoProfileRepository;

    public Member createMember(String token) {

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            log.info("responseCode : " + responseCode);

            if (responseCode != 200) {
                return null;
            }

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            // TODO: StringBuilder, String 차이점
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            log.info("response body : " + result);

            JsonElement element = JsonParser.parseString(result.toString());

            long id = element.getAsJsonObject().get("id").getAsLong();
            log.info("id : " + id);

            String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
            log.info("nickname : " + nickname);

            String profile_image = element.getAsJsonObject().get("properties").getAsJsonObject().get("profile_image").getAsString();
            log.info("profile_image :"+profile_image);

            String thumbnail_image = element.getAsJsonObject().get("properties").getAsJsonObject().get("thumbnail_image").getAsString();
            log.info("thumbnail_image :"+thumbnail_image);

            //이메일 추가시
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";
            if (hasEmail) {
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }
            log.info("email :" +email);

            br.close();

            // KakaoProfile Save
            KakaoProfile kakaoProfileParam = new KakaoProfile();
            kakaoProfileParam.setK_kakaoId(id);
            kakaoProfileParam.setK_nickname(nickname);
            kakaoProfileParam.setK_email(email);
            kakaoProfileParam.setK_profile_image_url(profile_image);
            kakaoProfileParam.setK_profile_image_url(thumbnail_image);

            KakaoProfile kakaoProfile = kakaoProfileRepository.save(kakaoProfileParam);

            // Member Save
            // setKakaoProfile
            Member memberParam = new Member();

            // TODO : 리펙토링 가능
            memberParam.settingKakaoProfile(kakaoProfile);
//            memberParam.setKakaoProfile(kakaoProfile);
//            memberParam.setKakaoId(kakaoProfile.getK_kakaoId());
//            memberParam.setNickname(kakaoProfile.getK_nickname());
//            memberParam.setEmail(kakaoProfile.getK_email());
//            memberParam.setProfile_image_url(kakaoProfile.getK_profile_image_url());
//            memberParam.setThumbnail_image_url(kakaoProfile.getK_thumbnail_image_url());

            return memberRepository.save(memberParam);


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
