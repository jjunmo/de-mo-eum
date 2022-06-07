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
import java.util.Optional;

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
            // StringBuilder, String 차이점 (https://proud-alder-ead.notion.site/String-StringBuilder-StringBuffer-be2a8c5d43ec4afe8cd90a42f2ffd9b9)
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            log.info("response body : " + result);

            JsonElement element = JsonParser.parseString(result.toString());

            long kakaoId = element.getAsJsonObject().get("id").getAsLong();
            log.info("카카오 id : " + kakaoId);

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
            
            
            // TODO: 기존에 가입된 아이디일경우 유효성 검사 코드가 필요할것같은데 어디에 작성하는게 좋을지? (수정)

            // KakaoProfile Save
            KakaoProfile kakaoProfileParam = new KakaoProfile();
            kakaoProfileParam.setKpKakaoId(kakaoId);
            kakaoProfileParam.setKpNickname(nickname);
            kakaoProfileParam.setKpEmail(email);
            kakaoProfileParam.setKpProfile_image_url(profile_image);
            kakaoProfileParam.setKpThumbnail_image_url(thumbnail_image);

            // 여기서 카카오 고유의 아이디 값으로 데이터베이스에 값을 확인 후 있다면  (2)
            KakaoProfile kakaoProfile;

            List<KakaoProfile> kakaoProfileList =kakaoProfileRepository.findBykpKakaoId(kakaoId);

            if (kakaoProfileList.isEmpty()) { // 값이 없다면
                kakaoProfile = kakaoProfileRepository.save(kakaoProfileParam);
                // Member Save
                // setKakaoProfile
                Member memberParam = new Member();
                memberParam.settingKakaoProfile(kakaoProfile);
                log.info("새로운 계정으로 생성");

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

    // TODO : 카카오계정 생성 이후 POSTMAN 확인시  ClassCastException 발생 (수정)
    // Entity 연관관계 설정 수정후 정상작동 확인
    // 새로운 회원 리스트를 만들어서 forEach로 똑같은 데이터를 넣은 후 리턴하는 이유를 모르겠습니다.
    public List<Member> getAllMember(){
//        try{
//            List<Member> members = memberRepository.findAll();
//            List<Member> customMember = new ArrayList<>();
//            members.forEach(e->{
//                Member member = new Member();
//                BeanUtils.copyProperties(e, member);
//                customMember.add(member);
//            });
//            return customMember;
//
//        }catch (ClassCastException e){
//            throw e;
//        }

        return memberRepository.findAll();
    }

    public List<Member> getMember(Long id){
        return memberRepository.findAllById(Collections.singleton(id));
    }

    // TODO : 어떤식으로 작성하는게 좋을지 ..? (수정)
    public Member updateMember(Long id , Member requestmember){
//        Member member = memberRepository.findById(id)
//                .orElseThrow(()-> new IllegalArgumentException("없는 아이디"));
//        member.setNickname(requestmember.getNickname());
//        member.setEmail(requestmember.getEmail());
//        member.setUpdateDate(LocalDateTime.now());

        // Code 변경
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isEmpty()) return null;

        Member member = memberOptional.get();
        member.setNickname(requestmember.getNickname());
        member.setEmail(requestmember.getEmail());
        member.setUpdateDate(LocalDateTime.now());
        return member;
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
