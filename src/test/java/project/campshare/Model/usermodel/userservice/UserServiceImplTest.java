package project.campshare.Model.usermodel.userservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.campshare.Model.usermodel.user.UserDto;
import project.campshare.Model.usermodel.userrepository.UserRepository;
import project.campshare.exception.PasswordMissMatchException;
import project.campshare.exception.UserDuplicateException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;

    private UserDto userDto;

    @BeforeEach
    public void addUser() {
        userDto = UserDto.builder()
                .email("test123@test.com")
                .password("test1234")
                .confirmPassword("test1234")
                .phone("01011112222")
                .nickname("17171771")
                .build();
    }


    @Test
    @DisplayName("닉네임 중복으로 인한 회원가입 실패")
    public void nicknameDuplicate() {
        when(userRepository.existByNickName("17171771")).thenReturn(true);

        assertThrows(UserDuplicateException.class, () -> userService.saveUser(userDto));

        verify(userRepository, atLeastOnce()).existByNickName("17171771");
    }

    @Test
    @DisplayName("이메일 중복으로 인한 회원가입 실패")
    public void emailDuplicate() {
        when(userRepository.existByEmail("test123@test.com")).thenReturn(true);

        assertThrows(UserDuplicateException.class, () -> userService.saveUser(userDto));

        verify(userRepository, atLeastOnce()).existByEmail("test123@test.com");
    }


    @Test
    @DisplayName("비밀번호 불일치로 인한 회원가입 실패")
    public void passwordMissMatch() {
        UserDto userDto2 = UserDto.builder()
                .email("test123@test.com")
                .password("test1234")
                .password("test123")
                .phone("01011112222")
                .nickname("17171771")
                .build();

        assertThrows(PasswordMissMatchException.class, () ->
                userService.saveUser(userDto2));
    }


}