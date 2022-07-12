package kr.co.promisemomo.module.member.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kr.co.promisemomo.module.common.Util;
import kr.co.promisemomo.module.member.entity.KakaoProfile;
import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.member.repository.KakaoProfileRepository;
import kr.co.promisemomo.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final KakaoProfileRepository kakaoProfileRepository;

    @Transactional
    public Member createMember(String token) {

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        //access_token 을 이용하여 사용자 정보 조회
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

            JsonElement element = Util.getJsonElement(conn);
            JsonObject asJsonObject = element.getAsJsonObject();
            JsonElement properties = asJsonObject.get("properties");

            long kakaoId = asJsonObject.get("id").getAsLong();
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String profile_image = properties.getAsJsonObject().get("profile_image").getAsString();
            String thumbnail_image = properties.getAsJsonObject().get("thumbnail_image").getAsString();

            log.info("카카오 id : " + kakaoId);
            log.info("nickname : " + nickname);
            log.info("profile_image :"+profile_image);
            log.info("thumbnail_image :"+thumbnail_image);

            KakaoProfile kakaoProfileParam = new KakaoProfile();

            //이메일 추가시
            boolean hasEmail = asJsonObject.get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";
            if (hasEmail) {
                kakaoProfileParam.setKpEmail(asJsonObject.get("kakao_account").getAsJsonObject().get("email").getAsString());
            }
            log.info("email :" +email);

            // KakaoProfile Save
            kakaoProfileParam.setKpKakaoId(kakaoId);
            kakaoProfileParam.setKpNickname(nickname);
            kakaoProfileParam.setKpProfile_image_url(profile_image);
            kakaoProfileParam.setKpThumbnail_image_url(thumbnail_image);

            // 여기서 카카오 고유의 아이디 값으로 데이터베이스에 값을 확인 후 있다면  (2)
            List<KakaoProfile> kakaoProfileList =kakaoProfileRepository.findBykpKakaoId(kakaoId);

            if (kakaoProfileList.isEmpty()) { // 값이 없다면

                KakaoProfile kakaoProfile = kakaoProfileRepository.save(kakaoProfileParam);


                log.info("새로운 계정으로 생성");
                // Member Save
                // setKakaoProfile
                Member memberParam = new Member();
                memberParam.settingKakaoProfile(kakaoProfile);
                return memberRepository.save(memberParam);
            }else{
                log.info("이미 있는 아이디입니다.");
                return memberRepository.findByKakaoId(kakaoId);
            }

            // (2) 카카오 프로필 테이블에 데이터가 있더라도 회원 테이블에 데이터가 없으면 문제 없음
            // 그런데 데이터가 있을 경우 return 해서 error 처리 (이미 존재하는 아이디 Or 로그인 처리) 해줘야함
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Member> getAllMember(){
        return memberRepository.findAll();
    }

    public Optional<Member> getMember(Long id){
        return memberRepository.findById(id);
    }

    @Transactional
    public Member updateMember(Long id , Member requestMember){
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isEmpty()) return null;

        Member member = memberOptional.get();
        member.setNickname(requestMember.getNickname());
        member.setEmail(requestMember.getEmail());
        return member;
    }

    @Transactional
    public String removeMember(Long id){
        Optional<Member> memberOptional =memberRepository.findById(id);
        if(memberOptional.isEmpty()) return "존재하지않는 아이디임.";
        memberRepository.deleteById(id);
        return "아이디 삭제";
    }

}
