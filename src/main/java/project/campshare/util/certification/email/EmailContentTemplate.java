package project.campshare.util.certification.email;

public class EmailContentTemplate {
   private String certificationNumber;
    public void setCertificationNumber(String certificationNumber) {
        this.certificationNumber = certificationNumber;
    }
    public String getCertificationContent(String certificationNumber) {
        return String.format("%s%s%s","{camp_share} 인증 번호: ",certificationNumber,"입니다.");
    }
}
