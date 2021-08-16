package project.campshare.Model.userlogin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.campshare.Model.usermodel.user.UserDto;
import project.campshare.domain.repository.UserRepository;
import project.campshare.encrypt.EncryptionService;
import project.campshare.exception.user.UnauthenticatedUserException;
import project.campshare.exception.user.UserNotFoundException;

import javax.servlet.http.HttpSession;

import static project.campshare.util.UserConstants.USER_ID;


@Service
@RequiredArgsConstructor
public class LoginService {
        private final HttpSession session;
        private final UserRepository userRepository;
        private final EncryptionService encryptionService;

    /**
     * 아이디 비밀번호 일치 여부
     * @param loginRequest
     */
    public void existByEmailAndPassword(UserDto.LoginRequest loginRequest) {
        loginRequest.passwordEncryption(encryptionService);
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        if (!userRepository.existsByEmailAndPassword(email, password)) {
            throw new UserNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    public void login(String email) {
        session.setAttribute(USER_ID, email);
    }

    public void logout() {
        session.removeAttribute(USER_ID);
    }

    public String getLoginUser() {
        return (String) session.getAttribute(USER_ID);
    }


    public UserDto.UserInfoDto getCurrentUser(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new UnauthenticatedUserException("존재하지 않는 사용자 입니다.")).toUserInfoDto();
    }
}
