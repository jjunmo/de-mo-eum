package kr.co.promisemomo.module.member.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kr.co.promisemomo.module.common.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
public class OauthService {

    public String getKakaoAccessToken (String code) {

        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 출력에 관련된 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            String sb = "grant_type=authorization_code" +
                        "&client_id=f398d5912c151f13e22b5fecfbd1f249" + // REST_API_KEY 입력
                        "&redirect_uri=http://localhost:8080/oauth/kakao" + //redirect_uri 입력
                        "&code=" + code;
            bw.write(sb);
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            log.info("responseCode : " + responseCode);

            if (responseCode != 200) {
                return "fail";
            }

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            JsonElement element = Util.getJsonElement(conn);
            JsonObject asJsonObject = element.getAsJsonObject();

            access_Token = asJsonObject.get("access_token").getAsString();
            refresh_Token = asJsonObject.get("refresh_token").getAsString();

            log.info("access_token : " + access_Token);
            log.info("refresh_token : " + refresh_Token);

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }
}
