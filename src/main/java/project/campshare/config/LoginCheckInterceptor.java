package project.campshare.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import project.campshare.domain.model.usermodel.user.User;
import project.campshare.domain.repository.UserRepository;
import project.campshare.logincommand.userlogin.SessionLoginService;
import project.campshare.annotation.LoginCheck;
import project.campshare.exception.user.UnauthenticatedUserException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequiredArgsConstructor
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final SessionLoginService loginService;
    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            LoginCheck loginCheck = handlerMethod.getMethodAnnotation(LoginCheck.class);

            if (loginCheck == null) {
                return true;
            }


            if (loginService.getLoginUser() == null) {
                throw new UnauthenticatedUserException("로그인 후 이용 가능합니다.");
            }


            LoginCheck.EmailAuthStatus authStatus = loginCheck.authority();
            if(authStatus == LoginCheck.EmailAuthStatus.AUTH){
                String email = loginService.getLoginUser();
                User user = userRepository.findByEmail(email).orElseThrow();
                if(!user.getEmailVerified()){
                    throw new UnauthenticatedUserException("이메일 인증 후 이용 가능합니다.");
                }
            }

        }
        return true;
    }
}