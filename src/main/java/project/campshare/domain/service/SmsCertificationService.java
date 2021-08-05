package project.campshare.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static project.campshare.util.UserConstants.CERTIFICATION_SESSION_KEY;
@RequiredArgsConstructor
@Service
public class SmsCertificationService {
    private final HttpSession session;

    public void setCertificationService(String certificationNumber) {
        session.setAttribute(CERTIFICATION_SESSION_KEY, certificationNumber);
        session.setMaxInactiveInterval(180);
    }

    public String getCertificationService() {
        return (String) session.getAttribute(CERTIFICATION_SESSION_KEY);
    }


}
