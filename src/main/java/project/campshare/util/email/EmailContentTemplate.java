package project.campshare.util.email;

public class EmailContentTemplate {
   private final String prefix = "{camp_share} 인증 번호: ";
   private final String suffix = "입니다.";
   private String certificationNumber;


    public void setCertificationNumber(String certificationNumber) {
        this.certificationNumber = certificationNumber;
    }

    public String parse(){
        return prefix.concat(certificationNumber).concat(suffix);
    }
}
