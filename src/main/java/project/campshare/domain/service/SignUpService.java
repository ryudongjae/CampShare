package project.campshare.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.campshare.Model.usermodel.user.User;
import project.campshare.Model.usermodel.user.UserDto;
import project.campshare.domain.repository.UserRepository;
import project.campshare.encrypt.EncryptionUtils;
import project.campshare.exception.DuplicateEmailException;
import project.campshare.exception.DuplicateNicknameException;


import java.util.List;
import java.util.Random;

import static project.campshare.util.UserConstants.NUMBER_GENERATIONS_COUNT;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final SmsCertificationService smsCertificationService;
    private final EncryptionUtils encryptionUtils;
    private final SmsSendService smsSendService;

    //데이터 조회용. 추후 삭제
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User saveUser(UserDto userDto){
        if(emailDuplicateCheck(userDto.getEmail())){
            throw new DuplicateEmailException("이미 존재하는 이메일 입니다.");
        }

        if (nicknameDuplicateCheck(userDto.getNickname())) {
            throw new DuplicateNicknameException("이미 존재하는 닉네임 입니다. 다른 닉네임을 사용해주세요.");
        }
        userDto.passwordEncryption(encryptionUtils);
        return userRepository.save(userDto.toUser());
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
        return userRepository.existsByNickName(nickname);
    }

    /**
     * 인증 번호 검증
     * @param certificationNumber
     * @return
     */
    public boolean certificationNumberInspection(String certificationNumber) {
        return smsCertificationService.getCertificationService().equals(certificationNumber);
    }

    /**
     * 인증번호 생성하는 로직
     */
    public void saveAuthenticationNumber(String phoneNumber) {
        Random rand = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < NUMBER_GENERATIONS_COUNT; i++) {
            stringBuilder.append((rand.nextInt(10)));
        }
        smsCertificationService.setCertificationService(stringBuilder.toString());
        smsSendService.sendMessage(phoneNumber , smsCertificationService.getCertificationService());
    }
}