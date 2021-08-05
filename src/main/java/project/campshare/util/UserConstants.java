package project.campshare.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserConstants {
    public static final int NUMBER_GENERATIONS_COUNT = 6;
    public static final String CERTIFICATION_SESSION_KEY = "certificationNumber";
    public static final ResponseEntity<String> RESPONSE_NICKNAME_CONFLICT = new ResponseEntity<>(
            "닉네임 중복", HttpStatus.CONFLICT);

    public static final ResponseEntity<String> RESPONSE_EMAIL_CONFLICT = new ResponseEntity<>(
            "이메일 중복", HttpStatus.CONFLICT);
}
