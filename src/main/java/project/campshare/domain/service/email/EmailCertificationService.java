package project.campshare.domain.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import project.campshare.Model.usermodel.user.UserDto.EmailCertificationRequest;
import project.campshare.dao.EmailCertificationDao;
import project.campshare.exception.certification.AuthenticationNumberMismatchException;
import project.campshare.util.certification.email.EmailContentTemplate;

import javax.naming.AuthenticationException;

import static project.campshare.util.RandomNumberGeneration.makeRandomNumber;
import static project.campshare.util.certification.email.EmailConstants.FROM_ADDRESS;
import static project.campshare.util.certification.email.EmailConstants.TITLE;

@Slf4j
@RequiredArgsConstructor
@Service
@ConfigurationProperties("certification-related-constants")
public class EmailCertificationService {

    private final JavaMailSender mailSender;
    private final EmailCertificationDao emailCertificationDao;
    private String emailFromAddress;


    //인증번호 전송 및 저장
    public void sendEmail(String email){
        String randomNumber = makeRandomNumber();
        String content = makeEmailContent(randomNumber);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(emailFromAddress);
        message.setSubject(TITLE);
        message.setText(content);
        mailSender.send(message);

        emailCertificationDao.createEmailCertification(email, randomNumber);

        log.info(emailCertificationDao.getEmailCertification(email));

    }

    //인증 이메일 내용 생성
    public String makeEmailContent(String certificationNumber) {
        EmailContentTemplate content = new EmailContentTemplate();
        return content.buildCertificationNumber(certificationNumber);
    }

    //인증번호 확인
    public void verifyEmail(EmailCertificationRequest  request){
        if(isVerify(request)){
            throw new AuthenticationNumberMismatchException("인증번호가 일치하지 않습니다.");
        }
        emailCertificationDao.removeEmailCertification(request.getEmail());
    }

    public boolean isVerify(EmailCertificationRequest request) {
        return !(emailCertificationDao.hasKey(request.getEmail()))&&
                emailCertificationDao.getEmailCertification(request.getEmail())
                        .equals(request.getCertificationNumber());
    }


}
