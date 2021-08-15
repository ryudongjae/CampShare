package project.campshare.util.certification.sms;

import lombok.Setter;

@Setter
public class SmsMessageTemplate {
    public String getCertificationNumber(String certificationNumber){
        return String.format("%s%s%s","[CAMP_SHARE] 인증번호: ",certificationNumber,"입니다.");
    }
}
