package project.campshare.exception;

public class PasswordMissMatchException extends RuntimeException{
    public PasswordMissMatchException(String message){
        super(message);
    }
}
