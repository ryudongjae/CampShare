package project.campshare.util.certification.sms;

import lombok.Setter;

@Setter
public class SmsMessageTemplate {
    private final String prefix = "[CAMP_SHARE] 인증번호: ";
    private final String suffix = "입니다. ";


    public String parse(String certificationNumber){
        return String.format("%s%s%s",prefix,certificationNumber,suffix);
    }
}
