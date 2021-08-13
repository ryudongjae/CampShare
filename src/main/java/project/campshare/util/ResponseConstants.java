package project.campshare.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseConstants {
    public static final ResponseEntity OK = ResponseEntity.ok().build();

    public static final ResponseEntity<Void> CREATED =
            ResponseEntity.status(HttpStatus.CREATED).build();


    public static final ResponseEntity BAD_REQUEST = ResponseEntity.badRequest().build();

    public static final ResponseEntity<String> DUPLICATION_NICKNAME = new ResponseEntity<>(
            "닉네임 중복", HttpStatus.CONFLICT);

    public static final ResponseEntity<String> DUPLICATION_EMAIL = new ResponseEntity<>(
            "이메일 중복", HttpStatus.CONFLICT);

    public static final ResponseEntity<String> USER_NOT_FOUND =
            new ResponseEntity<>(
                    "ID 또는 PW를 확인하세요.", HttpStatus.BAD_REQUEST
            );

    public static final ResponseEntity<String> LOGIN_UNAUTHORIZED =
            new ResponseEntity<>(
                    "Unauthenticated user", HttpStatus.UNAUTHORIZED
            );
}
