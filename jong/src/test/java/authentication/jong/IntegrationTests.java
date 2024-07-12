package authentication.jong;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import authentication.jong.DB.DatabaseCleanUp;
import authentication.jong.DTO.LoginDto;
import authentication.jong.DTO.MemberDto;
import authentication.jong.DTO.SignUpDto;
import authentication.jong.DTO.TokenDto;
import authentication.jong.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class IntegrationTests {
    
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    MemberService memberService;
    @Autowired
    DatabaseCleanUp databaseCleanUp;
    @LocalServerPort
    int randomServerPort;

    private SignUpDto signUpDto;
    private LocalDateTime now = LocalDateTime.now();


    @BeforeEach
    void BeforeEach() {
        signUpDto = SignUpDto.builder()
            .email("bwhdgus0429@gmail.com")
            .contact("010-9457-3161")
            .accountType("1")
            .lastAccessTime(now)
            .password("1234")
            .registrationData(now).build();
    }

    // @AfterEach
    // void afterEach(){
    //     databaseCleanUp.truncateAllEntity();
    // }

    @Test
    public void signUpTest(){
        String url = "http://localhost:" + randomServerPort + "/member/signup";
        ResponseEntity<MemberDto> responseEntity = testRestTemplate.postForEntity(url, signUpDto, MemberDto.class);

        // 응답 검증
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        MemberDto savedMemberDto = responseEntity.getBody();
        assertThat(savedMemberDto.getEmail()).isEqualTo(signUpDto.getEmail());
        assertThat(savedMemberDto.getContact()).isEqualTo(signUpDto.getContact());
    }

    @Test
    public void loginTest() {
        memberService.signUp(signUpDto);

        LoginDto loginDto = LoginDto.builder()
                .email("bwhdgus0429@gmail.com")
                .password("1234").build();
        

        // 로그인 요청
        TokenDto tokenDto = memberService.login(loginDto);

        // HttpHeaders 객체 생성 및 토큰 추가
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(tokenDto.getAccessToken());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        log.info("httpHeaders = {}", httpHeaders);

        // API 요청 설정
        String url = "http://localhost:" + randomServerPort + "/member/test";
        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(url, new HttpEntity<>(httpHeaders), String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("success");
    }    

    


}
