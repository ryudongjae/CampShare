package project.campshare.domain.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.campshare.Model.usermodel.user.UserDto;
import project.campshare.Model.usermodel.user.UserDto.SaveRequest;
import project.campshare.domain.repository.UserRepository;
import project.campshare.encrypt.EncryptionService;
import project.campshare.exception.user.DuplicateEmailException;
import project.campshare.exception.user.DuplicateNicknameException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    EncryptionService encryptionService;
    @InjectMocks
    UserService userService;

    private SaveRequest createUser() {
        SaveRequest saveRequest = SaveRequest.builder()
                .email("test123@test.com")
                .password("test1234")
                .phone("01011112222")
                .nickname("17171771")
                .build();
        return saveRequest;
    }

    @Test
    @DisplayName("이메일과 닉네임이 중복되지 않으면 회원가입에 성공한다.")
    public void signUp_Successful() {

        SaveRequest saveRequest = createUser();

        when(userRepository.existsByEmail("test123@test.com")).thenReturn(false);
        when(userRepository.existsByNickName("17171771")).thenReturn(false);

        userService.saveUser(saveRequest);

        verify(userRepository, atLeastOnce()).save(any());
    }

    @Test
    @DisplayName("이메일 중복으로 회원가입에 실패한다.")
    public void emailDuplicate() {
        SaveRequest saveRequest = createUser();
        when(userRepository.existsByEmail("test123@test.com")).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> userService.saveUser(saveRequest));

        verify(userRepository, atLeastOnce()).existsByEmail("test123@test.com");
    }

    @Test
    @DisplayName("닉네임 중복으로 회원가입에 실패한다.")
    public void nicknameDuplicate() {
        SaveRequest saveRequest = createUser();
        when(userRepository.existsByNickName("17171771")).thenReturn(true);

        assertThrows(DuplicateNicknameException.class, () -> userService.saveUser(saveRequest));

        verify(userRepository, atLeastOnce()).existsByNickName("17171771");
    }
}