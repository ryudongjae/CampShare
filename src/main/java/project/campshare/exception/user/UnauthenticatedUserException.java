package project.campshare.exception.user;

public class UnauthenticatedUserException extends RuntimeException{

    public UnauthenticatedUserException(String message) {
        super(message);
    }
}
