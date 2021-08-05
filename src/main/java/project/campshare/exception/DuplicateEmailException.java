package project.campshare.exception;

public class DuplicateEmailException extends IllegalArgumentException{
    public DuplicateEmailException(String message){
        super(message);
    }
}
