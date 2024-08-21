package kr.co.promisemomo.module.member.controller;

import com.sun.xml.bind.v2.TODO;
import kr.co.promisemomo.module.member.service.MemberService;
import kr.co.promisemomo.module.member.service.OauthService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class MemberRestControllerTest {
    /*보류*/
    //private final String KAKAO_LOGIN_URI = "kauth.kakao.com/oauth/authorize?client_id=f398d5912c151f13e22b5fecfbd1f249&redirect_uri=http://localhost:8080/oauth/kakao&response_type=code";

    private final String URL = "/oauth/kakao";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private OauthService oauthService;

    @Autowired
    private MemberService memberService;

    @BeforeAll
    public void memberSave(){

    }


    // MemberRestController [테스트 코드 작성]
    // 잘못된 접근입니다. (코드 오류) - 코드 값 안적어서 보내보기
    // 로그인을 실패했습니다. (카카오 정보 오류) - 코드 아무값 막 적어서 보내보기
    // 로그인을 실패했습니다. (토큰 오류) - 있을 수 없는 경우은데 그래도 혹시 몰라서 실패처리를 추가한 경우임
    // 성공

    // TODO:수정중 필요한가 [테스트 하는 방법이 따로 있음 - 이건 보류]
//    @Test
//    @DisplayName("RedirectURI 테스트")
//    public void callBackkRedirectUrit() throws Exception{
//        mockMvc.perform(
//                get(KAKAO_LOGIN_URI))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl(URL));
//    }


    @Test
    @DisplayName(value = "빈 Code")
    public void kakaoEmptyCode() throws Exception {

        // Error 내용이 Required request parameter 'code' for method parameter type String is not present
        mockMvc.perform(
                        get(URL)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.ALL))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName(value="임의의 Code (토큰 오류)")
    public void kakaoTrashCode() throws Exception {
        mockMvc.perform(
                get(URL)
                        .param("code","trashCode")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.ALL))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    // TODO : 테스트코드 작성 잘모르겠음.. 정보를 저장한 이후 테스트 적용해야할것같은데 Member에 id만 임의로 값 적용해서 테스트하면 될지? (수정)
    @Test
    @DisplayName(value="회원정보 수정")
    public void 회원정보수정() throws Exception{

        // memberSave() 메소드에서 먼저 회원 저장
        // @BeforeAll가 테스트 코드에서 뭔지 찾아보면 알수있음

        mockMvc.perform(
                put("/member/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.ALL))
                .andExpect(status().isBadRequest())
                .andDo(print());
            }
}