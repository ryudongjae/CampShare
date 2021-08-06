package project.campshare.exception.user;

public class DuplicateEmailException extends IllegalArgumentException{
    public DuplicateEmailException(String message){
        super(message);
    }
}
