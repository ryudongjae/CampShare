package project.campshare.exception.user;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException (String message){
        super(message);
    }
}
