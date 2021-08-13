package project.campshare.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import project.campshare.exception.certification.AuthenticationNumberMismatchException;
import project.campshare.exception.user.DuplicateEmailException;
import project.campshare.exception.user.DuplicateNicknameException;
import project.campshare.exception.user.UnauthenticatedUserException;
import project.campshare.exception.user.UserNotFoundException;

import java.time.LocalDateTime;

import static project.campshare.util.ResponseConstants.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateEmailException.class)
    protected final ResponseEntity<String> duplicateEmailException(DuplicateEmailException exception) {
        log.error("중복된 이메일입니다.",exception);
        return DUPLICATION_EMAIL;
    }

    @ExceptionHandler(DuplicateNicknameException.class)
    protected final ResponseEntity<String> duplicateNicknameException(DuplicateNicknameException exception) {


        log.error("중복된 닉네임" ,exception);

        return DUPLICATION_NICKNAME;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getFieldError().getDefaultMessage())
                .build();
        log.error(errorResponse.getMessage(),exception);
        return new ResponseEntity<>(errorResponse,errorResponse.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<String> handleUserNotFoundException(
            UserNotFoundException ex, WebRequest request) {
        log.error("Failed to signUp ::  {}, detection time={} ", request.getDescription(false),
                LocalDateTime.now(), ex);
        return USER_NOT_FOUND;
    }

    @ExceptionHandler(UnauthenticatedUserException.class)
    public final ResponseEntity<String> handleUnauthenticatedUserException(
            UnauthenticatedUserException ex, WebRequest request) {
        log.error("Failed to Execution ::  {}, detection time={} ", request.getDescription(false),
                LocalDateTime.now(), ex);
        return LOGIN_UNAUTHORIZED;
    }

    @ExceptionHandler(AuthenticationNumberMismatchException.class)
    public final ResponseEntity<Void>handleAuthenticationNumberMismatchException(AuthenticationNumberMismatchException ex,WebRequest request){
        log.error("인증번호가 일치하지 않습니다. : {} ,detection time={}",request.getDescription(false),LocalDateTime.now(),ex);
        return BAD_REQUEST;
    }

}
