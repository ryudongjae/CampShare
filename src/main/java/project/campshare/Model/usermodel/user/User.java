package project.campshare.Model.usermodel.user;

import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@Builder
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


    public UserDto.UserInfoDto toUserInfoDto() {
        return UserDto.UserInfoDto.builder()
                .email(this.getEmail())
                .nickname(this.getNickname())
                .phone(this.getPhone())
                .address(this.getAddress())
                .account(this.getAccount())
                .build();
    }

}
