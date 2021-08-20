package project.campshare.util.certification.email;

public class EmailContentTemplate {

    public String buildCertificationNumber(String certificationNumber){
        StringBuilder sb = new StringBuilder();
        sb.append("[camp-Share] 인증번호는 ");
        sb.append(certificationNumber);
        sb.append("입니다. ");

        return sb.toString();
    }

    public String buildEmailCheckContent(String token,String email) {
        StringBuilder builder = new StringBuilder();
        builder.append("http://localhost:8080/users/email-check-token?token=");
        builder.append(token);
        builder.append("&email=");
        builder.append(email);
        return builder.toString();
    }
}
