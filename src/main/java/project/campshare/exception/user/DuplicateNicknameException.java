package project.campshare.exception.user;

public class DuplicateNicknameException extends IllegalArgumentException{
    public DuplicateNicknameException(String message) {
        super(message);
    }
}
