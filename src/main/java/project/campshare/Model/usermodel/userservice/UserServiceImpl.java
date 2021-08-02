package project.campshare.Model.usermodel.userservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.campshare.Model.usermodel.user.UserDto;
import project.campshare.Model.usermodel.userrepository.UserRepository;
import project.campshare.exception.DuplicateEmailException;
import project.campshare.exception.DuplicateNicknameException;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean checkEmailUnique(String email) {
        return userRepository.existByEmail(email);
    }

    @Override
    public boolean checkNicknameUnique(String username) {
        return userRepository.existByUserName(username);
    }

    @Override
    public void save(UserDto.SaveRequest requestDto){
        if (checkEmailUnique(requestDto.getEmail())){
            throw new DuplicateEmailException();
        }
        if(checkNicknameUnique(requestDto.getNickname())){
            throw new DuplicateNicknameException();
        }
        userRepository.save(requestDto.toEntity());
    }




}
