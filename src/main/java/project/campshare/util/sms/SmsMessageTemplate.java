package project.campshare.util.sms;

import lombok.Setter;

@Setter
public class SmsMessageTemplate {
    private final String prefix = "[CAMP_SHARE] 인증번호: ";
    private final String suffix = "입니다. ";
    private String certificationNumber;

    public void setcertificationNumber(String certificationNumber) {
        this.certificationNumber = certificationNumber;
    }

    public String parse(){
        return prefix.concat(certificationNumber).concat(suffix);
    }
}
