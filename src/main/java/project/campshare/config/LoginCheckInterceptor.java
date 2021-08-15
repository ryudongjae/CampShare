package project.campshare.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import project.campshare.Model.userlogin.LoginService;
import project.campshare.annotation.LoginCheck;
import project.campshare.exception.user.UnauthenticatedUserException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequiredArgsConstructor
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler)
            throws Exception, UnauthenticatedUserException {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginCheck loginCheck = handlerMethod.getMethodAnnotation(LoginCheck.class);

        if (loginCheck == null) {
            return true;
        }

        if (loginService.getLoginUser() == null) {
            throw new UnauthenticatedUserException("로그인 후 이용 가능합니다.");
        }
        return true;

    }
}
