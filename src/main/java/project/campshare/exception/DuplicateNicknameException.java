package project.campshare.exception;

public class DuplicateNicknameException extends IllegalArgumentException{
    public DuplicateNicknameException(String message) {
        super(message);
    }
}
