package project.campshare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import project.campshare.Model.usermodel.user.UserDto;
import project.campshare.domain.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserApiControllerTest {
    @Autowired
    MockMvc mockMvc;
    //실제 객체와 비슷하지만 테스트에 필요한 기능만 가지는 가짜 객체를 만들어서 애플리케이션
    // 서버에 배포하지 않고도 스프링 MVC 동작을 재현할 수 있는 클래스를 의미합니다.

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
     UserRepository userRepository;

    @Test
    @DisplayName("회원가입 성공")
    public void signUpSuccess() throws Exception {
        UserDto userDto = userDto = UserDto.builder()
                .email("test123@test.com")
                .password("test1234")
                .confirmPassword("test1234")
                .phone("01011112222")
                .nickname("17171771")
                .build();

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/users/1"));
    }
    
    
    @Test
    void 회원가입_실패()throws Exception{
        //given
        UserDto userDto =UserDto.builder()
                .email("test123@naver.com")
                .nickname("1111")
                .password("test12")
                .phone("01001012323")
                .confirmPassword("test12")
                .build();
        //when
        
        //then
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    
    }
    
    
    @Test
    @DisplayName("패스워드 불일치")
    void passwordMissMatch()throws Exception{
        //given
        UserDto userDto  = UserDto.builder()
                .email("rdj1014@naver.com")
                .password("tt12")
                .confirmPassword("tt123")
                .phone("01012223334")
                .nickname("1212")
                .build();
        //when
        
        //then
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))//ObjectMapper 클래스의 writeValueAsString,
                // writeValueAsBytes는 Java 오브젝트로 부터 JSON을 만들고 이를 문자열 혹은 Byte 배열로 반환한다.
                .andDo(print())
                .andExpect(status().isBadRequest());

    
    }
}
