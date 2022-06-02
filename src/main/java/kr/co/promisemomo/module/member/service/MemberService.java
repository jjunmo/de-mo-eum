package kr.co.promisemomo.module.member.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import kr.co.promisemomo.module.member.entity.KakaoProfile;
import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.member.repository.KakaoProfileRepository;
import kr.co.promisemomo.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

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
            // TODO: StringBuilder, String 차이점 (https://proud-alder-ead.notion.site/String-StringBuilder-StringBuffer-be2a8c5d43ec4afe8cd90a42f2ffd9b9)
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
            
            
            // TODO: 기존에 가입된 아이디일경우 유효성 검사 코드가 필요할것같은데 어디에 작성하는게 좋을지?

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

            return memberRepository.save(memberParam);


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // TODO : 카카오계정 생성 이후 POSTMAN 확인시  ClassCastException 발생
    public List<Member> getAllMember(){

        try{
            List<Member> members = memberRepository.findAll();
            List<Member> customMember = new ArrayList<>();
            members.stream().forEach(e->{
                Member member = new Member();
                BeanUtils.copyProperties(e,member);
                customMember.add(member);
            });
            return customMember;

        }catch (ClassCastException e){
            throw e;
        }
    }

    public List<Member> getMember(Long id){
        return memberRepository.findAllById(Collections.singleton(id));
    }

    // TODO : 어떤식으로 작성하는게 좋을지 ..?
    public void updateMember(Long id , Member requestmember){
        Member member = memberRepository.findById(id)
                .orElseThrow(()->{
                        return new IllegalArgumentException("없는 아이디");
                        });
        member.setNickname(requestmember.getNickname());
        member.setEmail(requestmember.getEmail());
        member.setUpdateDate(LocalDateTime.now());
    }

    public void updateMember2(Member member){
        try{
            if(memberRepository.existsById(member.getId())){
                memberRepository.save(member);
            }
        }catch (Exception e){
            throw e;
        }
    }


    public String removeMember(Member member){
        try{
            if(memberRepository.existsById(member.getId())){
                memberRepository.delete(member);
                return "아이디 삭제 성공.";
            }else{
                return "아이디가 없다.";
            }
        }catch (Exception e){
            throw e;
        }
    }


}
