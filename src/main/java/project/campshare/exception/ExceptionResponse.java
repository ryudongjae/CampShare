package project.campshare.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ExceptionResponse {

    private LocalDateTime timestamp;
    private String message;
    private String detail;
}
