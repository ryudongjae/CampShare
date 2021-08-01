package project.campshare.Model.usermodel.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UserSaveDto {
    @NotBlank(message = "닉네임을 입력하세요")
    @Size(min =2,max = 10,message = "닉네임은 2자 이상 10자 이하로 입력하세요.")
    private String nickname;

    @NotBlank(message = "이메일을 입력하세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요")
    private String password;

    private String confirmPassword;

    @NotBlank(message = "전화번호를 입력하세요")
    @Size(min = 8,max = 20,message = "8자 이상 20자 이하로 입력하세요.")
    private String phoneNumber;

    @Builder
    public UserSaveDto(String nickname, String email,
                       String password, String confirmPassword,
                       String phoneNumber) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
    }

    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .build();
    }
}
