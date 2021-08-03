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
import project.campshare.Model.usermodel.user.User;
import project.campshare.Model.usermodel.user.UserDto;
import project.campshare.Model.usermodel.userrepository.UserRepository;
import project.campshare.Model.usermodel.userservice.UserServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserApiControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원 가입 성공")
    public void signUpSuccess() throws Exception {
        UserDto userDto = userDto = UserDto.builder()
                .email("test123@test.com")
                .password("test1234")
                .phone("01011112222")
                .nickname("17171771")
                .build();

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/users/1"));
        User resultUser = userRepository.findAll().get(0);
        assertAll(
                () -> assertThat(resultUser.getEmail()).isEqualTo("test123@test.com"),
                () -> assertThat(resultUser.getNickname()).isEqualTo("17171771"),
                () -> assertThat(resultUser.getPhone()).isEqualTo("01011112222")
        );
    }

}