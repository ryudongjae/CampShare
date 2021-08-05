package project.campshare.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static project.campshare.util.UserConstants.RESPONSE_EMAIL_CONFLICT;
import static project.campshare.util.UserConstants.RESPONSE_NICKNAME_CONFLICT;

@Slf4j
@RestControllerAdvice
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public final ResponseEntity<String> handleEmailDuplicateException(DuplicateEmailException ex, WebRequest request){
        log.error("failed to signUp :: {}, detection time ={}",request.getDescription(false),
                LocalDateTime.now(), ex);
        return RESPONSE_EMAIL_CONFLICT;

    }

    @ExceptionHandler(DuplicateNicknameException.class)
    public final ResponseEntity<String> handleNickNameDuplicateException(DuplicateNicknameException ex,WebRequest request){
        log.error("failed to signUp :: {} , detection time ={} ", request.getDescription(false),
                LocalDateTime.now(),ex);
        return RESPONSE_NICKNAME_CONFLICT;
    }
}