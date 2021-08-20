package project.campshare.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import project.campshare.exception.certification.AuthenticationNumberMismatchException;
import project.campshare.exception.product.ProductNotFoundException;
import project.campshare.exception.user.*;

import java.time.LocalDateTime;

import static project.campshare.util.ResponseConstants.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateEmailException.class)
    protected final ResponseEntity<String> duplicateEmailException(DuplicateEmailException ex, WebRequest request) {
        log.debug("Duplicate email :: {}, detection time = {}",request.getDescription(false));
        return DUPLICATION_EMAIL;
    }

    @ExceptionHandler(DuplicateNicknameException.class)
    protected final ResponseEntity<String> duplicateNicknameException(DuplicateNicknameException ex, WebRequest request) {
        log.debug("Duplicate nickname :: {}, detection time = {}",request.getDescription(false));
        return DUPLICATION_NICKNAME;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getFieldError().getDefaultMessage(),ex);
        return new ResponseEntity<>(ex.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        log.debug("로그인 실패 : 존재하지 않는 ID 또는 패스워드 불일치",request.getDescription(false));
        return USER_NOT_FOUND;
    }

    @ExceptionHandler(UnauthenticatedUserException.class)
    public final ResponseEntity<String> handleUnauthenticatedUserException(
            UnauthenticatedUserException ex, WebRequest request) {
        log.error("Failed to Execution ::  {}, detection time={} ", request.getDescription(false),
                LocalDateTime.now(), ex);
        return UNAUTHORIZED_USER;
    }

    @ExceptionHandler(AuthenticationNumberMismatchException.class)
    public final ResponseEntity<Void>handleAuthenticationNumberMismatchException(AuthenticationNumberMismatchException ex){
        log.debug("인증번호 불일치",ex);
        return BAD_REQUEST;
    }


    @ExceptionHandler(WrongPasswordException.class)
    public final ResponseEntity<String> handleWrongPasswordException(WrongPasswordException ex,WebRequest request){
        log.debug("Wrong_password :: {}, detection time = {}",request.getDescription(false));
        return WRONG_PASSWORD;
    }

    @ExceptionHandler(UnableToChangeNicknameException.class)
    public final ResponseEntity<String> handleUnableToChangeNicknameException(UnableToChangeNicknameException ex) {
        log.error("닉네임은 7일에 한번만 변경 가능합니다.",ex);
        return FAIL_TO_CHANGE_NICKNAME;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<String> productNotFoundException(ProductNotFoundException ex){
        log.debug("존재하지 않는 상품입니다.",ex);
        return PRODUCT_NOT_FOUND;
    }


}
