package project.campshare.domain.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import project.campshare.Model.usermodel.user.UserDto;
import project.campshare.Model.usermodel.user.UserDto.EmailCertificationRequest;
import project.campshare.dao.EmailCertificationDao;
import project.campshare.util.email.EmailContentTemplate;

import static project.campshare.util.UserConstants.makeRandomNumber;
import static project.campshare.util.email.EmailConstants.FROM_ADDRESS;
import static project.campshare.util.email.EmailConstants.TITLE;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailCertificationService {
    private final JavaMailSender mailSender;
    private final EmailCertificationDao emailCertificationDao;


    //인증번호 전송 및 저장
    public void sendEmail(String email){
        String randomNumber = makeRandomNumber();
        String content = makeEmailContent(randomNumber);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(FROM_ADDRESS);
        message.setSubject(TITLE);
        message.setText(content);
        mailSender.send(message);

        emailCertificationDao.createEmailCertification(email, randomNumber);

        log.info(emailCertificationDao.getEmailCertification(email));

    }

    //인증 이메일 내용 생성
    public String makeEmailContent(String certificationNumber) {
        EmailContentTemplate content = new EmailContentTemplate();
        content.setCertificationNumber(certificationNumber);
        return content.parse();
    }

    //인증번호 확인
    public boolean verifyEmail(EmailCertificationRequest requestDto) {
        if (emailCertificationDao.hasKey(requestDto.getEmail()) &&
                emailCertificationDao.getEmailCertification(requestDto.getEmail()).equals(requestDto.getCertificationNumber())) {
            emailCertificationDao.removeSmsCertification(requestDto.getEmail());
            return true;
        }
        return false;
    }


}
