package project.campshare.Model.usermodel.user;

import lombok.*;
import project.campshare.Model.usermodel.user.address.Address;
import project.campshare.Model.usermodel.user.address.AddressBook;
import project.campshare.exception.user.UnableToChangeNicknameException;

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

    private LocalDateTime nicknameModifiedDate;

    @Column(name = "USER_PHONENUMBER")
    private String phone;


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

    public void updateAccount(Account account){
        this.account = account;
    }

    public void addAddressBook(Address address){
        this.addressBook.add(new AddressBook(address));
    }


    public void updateNickname(SaveRequest request){
        if(canModifiedNickname()){
            throw new UnableToChangeNicknameException("닉네임은 7일에 한번만 변경할 수 있습니다.");
        }
    }

    private boolean canModifiedNickname() {
        return !(this.nicknameModifiedDate.isBefore(LocalDateTime.now().minusDays(7)));
    }


}
