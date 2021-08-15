package project.campshare.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.campshare.Model.usermodel.user.User;
import project.campshare.domain.repository.UserRepository;
import project.campshare.encrypt.EncryptionService;
import project.campshare.exception.user.WrongPasswordException;
import project.campshare.exception.user.DuplicateEmailException;
import project.campshare.exception.user.DuplicateNicknameException;
import project.campshare.exception.user.UnauthenticatedUserException;
import project.campshare.exception.user.UserNotFoundException;


import java.util.List;

import static project.campshare.Model.usermodel.user.UserDto.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final EncryptionService encryptionService;


    //데이터 조회용. 추후 삭제
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //이메일 중복과 닉네임 중복 exception 분리하여 예외의 원인을 정확히 파악하도록 구현
    public User saveUser(SaveRequest userDto){
        if(emailDuplicateCheck(userDto.getEmail())){
            throw new DuplicateEmailException("이미 존재하는 이메일 입니다.");
        }

        if (nicknameDuplicateCheck(userDto.getNickname())) {
            throw new DuplicateNicknameException("이미 존재하는 닉네임 입니다. 다른 닉네임을 사용해주세요.");
        }
        userDto.passwordEncryption(encryptionService);
        return userRepository.save(userDto.toEntity());
    }

    /**
     * 이메일 체크
     * @param email
     * @return
     */
    public boolean emailDuplicateCheck(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * 닉네임 체크
     * @param nickname
     * @return
     */
    public boolean nicknameDuplicateCheck(String nickname) {
        return userRepository.existsByNickname(nickname);
    }


    public FindUserResponse getUserResource(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 email 입니다.")).toFindUserDto();
    }

    /**
     * 비밀 번호 변경
     * @param requestDto
     */
    @Transactional
    public void updatePasswordByForget(ChangePasswordRequest requestDto){
        String email = requestDto.getEmail();
        requestDto.passwordEncryption(encryptionService);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthenticatedUserException("Unauthenticated User"));

        user.updatePassword(requestDto.getPasswordAfter());
    }
    @Transactional
    public void updatePassword(String email , ChangePasswordRequest request){
        request.passwordEncryption(encryptionService);
        String passwordBefore = request.getPasswordBefore();
        String passwordAfter = request.getPasswordAfter();
        System.out.println(email);
        if(!userRepository.existsByEmailAndPassword(email,passwordBefore)){
            throw new UnauthenticatedUserException("이전 비밀번호가 일치하지 않습니다.");
        }

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UnauthenticatedUserException("Unauthenticated user"));

        user.updatePassword(passwordAfter);
    }


    @Transactional
    public void delete(String email, String password) {
        if(!userRepository.existsByEmailAndPassword(email,encryptionService.encrypt(password))){
            throw new WrongPasswordException();
        }

        userRepository.deleteByEmail(email);
    }
}