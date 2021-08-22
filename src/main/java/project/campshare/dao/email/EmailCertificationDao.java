package project.campshare.dao.email;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;


public interface EmailCertificationDao {
    void createEmail(String email,String certificationNumber);
    String getEmailCertification(String email);
    void removeEmailCertification(String email);
    boolean hasKey(String email);
}
