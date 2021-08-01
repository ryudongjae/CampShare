package project.campshare.Model.usermodel.userservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.campshare.Model.usermodel.user.UserSaveDto;
import project.campshare.Model.usermodel.userrepository.UserRepository;
import project.campshare.exception.DuplicateEmailException;
import project.campshare.exception.DuplicateNicknameException;
import project.campshare.exception.WrongConfirmPasswordException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean checkEmailUnique(String email) {
        return !userRepository.existByEmail(email);
    }

    @Override
    public boolean checkNicknameUnique(String username) {
        return !userRepository.existByUserName(username);
    }

    @Override
    public boolean checkPasswordUnique(String password,String confirmPassword) {
        return password.equals(confirmPassword);
    }

    @Transactional
    public void save(UserSaveDto requestDto){
        if(!checkEmailUnique(requestDto.getEmail())){
            throw new DuplicateEmailException();
        }
        if(!checkNicknameUnique(requestDto.getNickname())){
            throw new DuplicateNicknameException();
        }
        if(!checkPasswordUnique(requestDto.getPassword(), requestDto.getConfirmPassword())){
            throw new WrongConfirmPasswordException();

        }
        userRepository.save(requestDto.toEntity());

    }
}
