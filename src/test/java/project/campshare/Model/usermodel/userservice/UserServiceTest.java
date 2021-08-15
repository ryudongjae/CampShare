package project.campshare.Model.usermodel.userservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import project.campshare.Model.usermodel.user.User;
import project.campshare.Model.usermodel.user.UserDto;
import project.campshare.Model.usermodel.user.UserDto.ChangePasswordRequest;
import project.campshare.Model.usermodel.user.UserDto.SaveRequest;
import project.campshare.domain.repository.UserRepository;
import project.campshare.domain.service.UserService;
import project.campshare.encrypt.EncryptionService;
import project.campshare.exception.user.DuplicateEmailException;
import project.campshare.exception.user.DuplicateNicknameException;
import project.campshare.exception.user.UserNotFoundException;
import project.campshare.exception.user.WrongPasswordException;


import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static project.campshare.Model.usermodel.user.UserDto.ChangePasswordRequest.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Mock
    EncryptionService encryptionService;

   private SaveRequest createUser(){
       SaveRequest saveRequest = SaveRequest.builder()
               .email("rdj10149@gmail.com")
               .nickname("ryu")
               .phone("01000001111")
               .password("ryu11")
               .build();

       return saveRequest;
   }

   @Test
   @DisplayName("이메일과 닉네임이 중복되지 않으면 회원가입에 성공")
   void signUp_Success()throws Exception{
       SaveRequest saveRequest =  createUser();
       when(userRepository.existsByEmail("rdj10149@gmail.com")).thenReturn(false);
       when(userRepository.existsByNickname("ryu")).thenReturn(false);

       userService.saveUser(saveRequest);
        //userRepository에서 save를 한번이상 호출 했는지 체크
       verify(userRepository, Mockito.atLeastOnce()).save(any());

   }

    @Test
    @DisplayName("이메일 중복으로 인해 가입 실패")
    void emailDuplicate()throws Exception{
        //given
        SaveRequest saveRequest = createUser();
        //when
        when(userRepository.existsByEmail("rdj10149@gmail.com")).thenReturn(true);
        //then
        Assertions.assertThrows(DuplicateEmailException.class, () -> userService.saveUser(saveRequest));
        verify(userRepository, atLeastOnce()).existsByEmail("rdj10149@gmail.com");
    }

    @Test
    @DisplayName("닉네임 중복으로 인해 가입 실패")
    void nicknameDuplicate()throws Exception{
        //given
        SaveRequest saveRequest = createUser();
        //when
        when(userRepository.existsByNickname("ryu")).thenReturn(true);
        //then
        Assertions.assertThrows(DuplicateNicknameException.class , ()->userService.saveUser(saveRequest));
        verify(userRepository, atLeastOnce()).existsByNickname("ryu");


    }


    /**
     * 비밀번호 찾기 관련 테스트 코드 
     */
    @Test
    @DisplayName("비밀번호 찾기 성공, 전달받은 객체가 회원이라면 비밀번호 변경에 성공한다.")
    void updatePassword()throws Exception{
        //given
        ChangePasswordRequest changePasswordRequest = of("rdj10149@gmail.com","ryu11");
        User user = createUser().toEntity();

        //when
        when(userRepository.findByEmail(changePasswordRequest.getEmail())).thenReturn(Optional.of(user));
        userService.updatePassword(changePasswordRequest);

        //then
        assertThat(user.getPassword()).isEqualTo(changePasswordRequest.getPassword());
        verify(userRepository,atLeastOnce()).findByEmail(changePasswordRequest.getEmail());


    }
    
    @Test
    @DisplayName("가입된 이메일 입력시 비밀번호 찾기에 필요한 리소스 반환")
    void SuccessToGetUserResource()throws Exception{
        //given
        String email = "rdj10149@gmail.com";
        User user = createUser().toEntity();


        //when
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        UserDto.FindUserResponse userResource = userService.getUserResource(email);

        //then
        assertThat(userResource.getEmail()).isEqualTo(user.getEmail());
        assertThat(userResource.getPhone()).isEqualTo(user.getPhone());
        

    
    }


    @Test
    @DisplayName("존재하지 않는 이메일 입력시 비밀번호 찾기에 필요한 리소스 리턴에 실패한다.")
    void failToGetUserResource()throws Exception{
        //given
        String email = "rdj10149@gmail.com";



        //when
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(UserNotFoundException.class,()-> userService.getUserResource("rdj10149@gmail.com"));
        verify(userRepository, atLeastOnce()).findByEmail(email);


    }


    @Test
    @DisplayName("비밀번호가 일치하지 않아 회원 탈퇴 실패")
    void deleteFailure()throws Exception{
        //given
        SaveRequest saveRequest = createUser();
        String email = saveRequest.getEmail();
        String password = saveRequest.getPassword();

        //when
        when(userRepository.existsByEmailAndPassword(email,encryptionService.encrypt(password))).thenReturn(false);
        //then

        Assertions.assertThrows(WrongPasswordException.class, ()->userService.delete(email,password));
        verify(userRepository,never()).deleteByEmail(email);


    }

    @Test
    @DisplayName("비밀번호가 일치해 회원 탈퇴 성공")
    void deleteSuccess()throws Exception{
        //given
        SaveRequest saveRequest = createUser();
        String email = saveRequest.getEmail();
        String password = saveRequest.getPassword();

        //when
        when(userRepository.existsByEmailAndPassword(email,encryptionService.encrypt(password))).thenReturn(true);
        userService.delete(email,password);

        //then
        verify(userRepository,atLeastOnce()).deleteByEmail(email);


    }
}