package project.campshare.exception;

public class UserDuplicateException extends RuntimeException{
    public UserDuplicateException(String message){
        super(message);
    }
}
