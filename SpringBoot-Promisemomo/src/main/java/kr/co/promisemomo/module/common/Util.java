package kr.co.promisemomo.module.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Util {
    public static JsonElement getJsonElement(HttpURLConnection conn) throws IOException {
        //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
        // StringBuilder, String 차이점 (https://proud-alder-ead.notion.site/String-StringBuilder-StringBuffer-be2a8c5d43ec4afe8cd90a42f2ffd9b9)
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        StringBuilder result = new StringBuilder();

        while ((line = br.readLine()) != null) {
            result.append(line);
        }
        br.close();
        //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
        //deprecated
        //JsonParser parser = new JsonParser();
        return JsonParser.parseString(result.toString());
    }
}
