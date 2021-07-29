package project.campshare.encrypt;

import java.security.NoSuchAlgorithmException;

/**
 * 암호화
 */
public interface Encryptor {

    String encrypt(String message);
}
