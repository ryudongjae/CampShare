package project.campshare.Model.usermodel.userservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.campshare.Model.usermodel.user.UserDto;

import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * 문자 인증
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class CertificationService {
    private final HttpSession session;
    //문자 보내기
    public void sendSms(String phoneNumber) {
        String randomNumber = makeRandomNumber();
        log.info("인증 번호 :" + randomNumber);

    }
    //6자리 난수 생성
    public String makeRandomNumber() {
        Random random = new Random();
        return String.valueOf(10000 + random.nextInt(90000));
    }
    // 인증번호가 세션에 발급된 인증번호와 동일한지 체크
    public boolean phoneVerification(UserDto.CertificationRequest requestDto) {
        if (session.getAttribute(requestDto.getPhoneNumber()).equals(requestDto.getCertificationNumber())){
            session.removeAttribute(requestDto.getPhoneNumber());
            return true;
        }
        return false;
    }
}
