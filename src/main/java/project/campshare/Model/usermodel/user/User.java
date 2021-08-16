package project.campshare.Model.usermodel.user;

import jdk.dynalink.linker.LinkerServices;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static project.campshare.Model.usermodel.user.UserDto.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_EMAIL")
    private String email;

    @Column(name = "USER_PASSWORD")
    private String password;

    @Column(name = "USER_NICKNAME")
    private String nickname;

    @Column(name = "USER_PHONENUMBER")
    private String phone;

    @Embedded
    private Address address;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "USER_ID")
    private List<AddressBook> addressBook = new ArrayList<>();

    @Embedded
    @Column(name = "USER_ACCOUNT")
    private Account account;

    public UserInfoDto toUserInfoDto() {
        return UserInfoDto.builder()
                .email(this.getEmail())
                .nickname(this.getNickname())
                .phone(this.getPhone())
                .address(this.getAddress())
                .account(this.getAccount())
                .build();
    }

    public FindUserResponse toFindUserDto(){
        return FindUserResponse.builder()
                .email(this.getEmail())
                .phone(this.getPhone())
                .build();
    }


    public void updatePassword(String password){
        this.password = password;
    }

    public void updateAccount(String bankName,String accountNumber,String depositor){
        Account account = new Account(bankName,accountNumber,depositor);
        this.account = account;
    }
}
