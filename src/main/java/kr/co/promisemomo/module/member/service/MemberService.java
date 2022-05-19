package kr.co.promisemomo.module.member.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import kr.co.promisemomo.module.member.entity.KakaoProfile;
import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.member.repository.KakaoProfileRepository;
import kr.co.promisemomo.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class MemberService {

    // TODO: 이것은 잘못된 코드
    private final MemberRepository memberRepository;
    private final KakaoProfileRepository kakaoProfileRepository;

    public Member createKakaoUser(String token)  {

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
            System.out.println("responseCode : " + responseCode);

            if (responseCode != 200) {
                return null;
            }

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonElement element = JsonParser.parseString(result);

            long id = element.getAsJsonObject().get("id").getAsLong();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";
            if(hasEmail){
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }

            System.out.println("id : " + id);
            System.out.println("email : " + email);

            br.close();

            // KakaoProfile Save
            KakaoProfile kakaoProfileParam = new KakaoProfile();
            kakaoProfileParam.setK_kakaoId(id);

            KakaoProfile kakaoProfile = kakaoProfileRepository.save(kakaoProfileParam);

            // Member Save
            // setKakaoProfile
            Member memberParam = new Member();
            memberParam.setKakaoProfile(kakaoProfile);

            return memberRepository.save(memberParam);


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
