package project.campshare.encrypt;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SHA256Encryptor implements Encryptor {
    @Override
    public String encrypt(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] passBytes = message.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuilder sb = new StringBuilder();

            for (byte b : digested) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));

            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("지원하지 않는 암호화 방식입니다.", e);
        }
    }

}




