package project.campshare.Model.usermodel.user;

import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String nickName;

    private String phone;

    private LocalDateTime createdAt;

    @Embedded
    private Address address;

    @Embedded
    private Account account;

    @Getter
    public static class CertificationInfo {
        private String number;
    }

}
