package project.campshare.Model.usermodel.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import project.campshare.encrypt.EncryptionUtils;
import project.campshare.encrypt.SHA256Encryptor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Builder
public class UserDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;

    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{3,20}$")
    private String nickname;

    @NotBlank
    @Length(min = 10, max = 11)
    private String phone;

    public void passwordEncryption(EncryptionUtils encryptionUtils) {
        this.password = encryptionUtils.encrypt(password);
    }

    public User toUser() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .phone(this.phone)
                .build();
    }

    @Getter
    public static class CertificationInfo {

        private String certificationNumber;
        private String phoneNumber;
    }

}
