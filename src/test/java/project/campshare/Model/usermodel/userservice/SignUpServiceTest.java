//package project.campshare.Model.usermodel.userservice;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import project.campshare.Model.usermodel.user.UserDto;
//import project.campshare.domain.repository.UserRepository;
//import project.campshare.domain.service.SignUpService;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;
//
//@ExtendWith(MockitoExtension.class)
//class SignUpServiceTest {
//    @Mock
//    UserRepository userRepository;
//    @InjectMocks
//    SignUpService userService;
//
//    private UserDto userDto;
//
//    @BeforeEach
//    public void addUser() {
//        userDto = UserDto.builder()
//                .email("test123@test.com")
//                .password("test1234")
//                .phone("01011112222")
//                .nickname("17171771")
//                .build();
//    }
//
//
//    @Test
//    @DisplayName("회원가입 실패_ 닉네임 중복")
//    public void nicknameDuplicate() {
//        when(userRepository.existsByNickName("17171771")).thenReturn(true);
//
//        assertThrows(UserDuplicateException.class, () -> userService.saveUser(userDto));
//
//        verify(userRepository, atLeastOnce()).existsByNickName("17171771");
//    }
//
//    @Test
//    @DisplayName("회원가입 실패_ 이메일 중복 ")
//    public void emailDuplicate() {
//        when(userRepository.existsByEmail("test123@test.com")).thenReturn(true);
//
//        assertThrows(UserDuplicateException.class, () -> userService.saveUser(userDto));
//
//        verify(userRepository, atLeastOnce()).existsByEmail("test123@test.com");
//    }
//
//}