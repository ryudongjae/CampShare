package project.campshare.Model.usermodel.user;

import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String phone;

    @Embedded
    private Address address;

    @Embedded
    private Account account;



}
