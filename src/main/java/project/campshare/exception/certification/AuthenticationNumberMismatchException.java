package project.campshare.exception.certification;

import javax.naming.AuthenticationException;

public class AuthenticationNumberMismatchException extends RuntimeException {
    public AuthenticationNumberMismatchException(String message){
        super(message);
    }
}
