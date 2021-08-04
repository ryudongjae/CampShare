package project.campshare.Model.usermodel.userservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import project.campshare.Model.usermodel.user.User;
import project.campshare.Model.usermodel.user.UserDto;
import project.campshare.Model.usermodel.userrepository.UserRepository;
import project.campshare.exception.PasswordMissMatchException;
import project.campshare.exception.UserDuplicateException;
import project.campshare.util.SessionUtils;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final UserRepository userRepository;
    private final SessionUtils sessionUtils;

    //데이터 조회용. 추후 삭제
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User saveUser(UserDto userDto) {
        signUpValid(userDto);
        sessionUtils.removeCertificationSession();
        User user = userDto.toUser();
        return userRepository.save(user);
    }

    private void signUpValid(UserDto userDto) {

        if (emailDuplicateCheck(userDto.getEmail()) || nicknameDuplicateCheck(userDto.getNickname())) {
            throw new UserDuplicateException("이메일 또는 닉네임을 확인하세요.");
        }

        if (checkPassword(userDto.getPassword(), userDto.getConfirmPassword())) {
            throw new PasswordMissMatchException("비밀번호가 일치하지 않습니다.");
        }
    }

    private boolean checkPassword(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }


    public boolean emailDuplicateCheck(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean nicknameDuplicateCheck(String nickname) {
        return userRepository.existsByNickName(nickname);
    }

    public boolean certificationNumberInspection(String certificationNumber) {
        return sessionUtils.getCertificationSession().equals(certificationNumber);
    }

    public void saveAuthenticationNumber() {
        Random rand = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            stringBuffer.append((rand.nextInt(10)));
        }
        sessionUtils.setCertificationSession(stringBuffer.toString());
        System.out.println(sessionUtils.getCertificationSession());
    }
}