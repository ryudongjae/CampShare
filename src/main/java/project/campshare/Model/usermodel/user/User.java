package project.campshare.Model.usermodel.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity{
    @Id@GeneratedValue
    private Long id;

    private String nickname;

    private String email;

    private String password;

    private String phoneNumber;

    @Builder
    public User(String nickname, String email, String password, String phoneNumber) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
