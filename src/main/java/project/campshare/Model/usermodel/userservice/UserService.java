package project.campshare.Model.usermodel.userservice;

public interface UserService {

    boolean checkEmailUnique(String email);
    boolean checkNicknameUnique(String username);
    boolean checkPasswordUnique(String password,String confirmPassword);

}
