package project.campshare.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import project.campshare.exception.user.DuplicateEmailException;
import project.campshare.exception.user.DuplicateNicknameException;
import project.campshare.exception.user.UnauthenticatedUserException;

import java.time.LocalDateTime;

import static project.campshare.util.ResponseConstants.LOGIN_UNAUTHORIZED;
import static project.campshare.util.ResponseConstants.UNAUTHORIZED;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateEmailException.class)
    protected ResponseEntity<ErrorResponse> duplicateEmailException(DuplicateEmailException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("중복된 이메일 입니다.")
                .build();

        log.error(errorResponse.getMessage(),exception);
        return new ResponseEntity<>(errorResponse,errorResponse.getStatus());
    }

    @ExceptionHandler(DuplicateNicknameException.class)
    protected ResponseEntity<ErrorResponse> duplicateNicknameException(DuplicateNicknameException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("중복된 이메일 입니다.")
                .build();

        log.error(errorResponse.getMessage(),exception);

        return new ResponseEntity<>(errorResponse,errorResponse.getStatus());
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
        return UNAUTHORIZED;
    }

    @ExceptionHandler(UnauthenticatedUserException.class)
    public final ResponseEntity<String> handleUnauthenticatedUserException(
            UnauthenticatedUserException ex, WebRequest request) {
        log.error("Failed to Execution ::  {}, detection time={} ", request.getDescription(false),
                LocalDateTime.now(), ex);
        return LOGIN_UNAUTHORIZED;
    }


}
