package project.campshare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import project.campshare.domain.model.user.Account;
import project.campshare.domain.model.user.User;
import project.campshare.domain.model.user.UserLevel;
import project.campshare.domain.model.user.address.Address;
import project.campshare.encrypt.EncryptionService;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Builder
public class UserDto {
    @Getter
    @NoArgsConstructor
    public static class SaveRequest {
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

        public void passwordEncryption(EncryptionService encryptionService) {
            this.password = encryptionService.encrypt(password);
        }
        @Builder
        public SaveRequest(String email,String password,String confirmPassword,String nickname, String phone) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
            this.phone = phone;
        }

        public User toEntity() {
            return User.builder()
                    .email(this.email)
                    .password(this.password)
                    .nicknameModifiedDate(LocalDateTime.now())
                    .nickname(this.nickname)
                    .phone(this.phone)
                    .userLevel(UserLevel.USER)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class SmsCertificationRequest {
        private String phone;
        private String certificationNumber;
    }

    @Getter
    @NoArgsConstructor
    public static class EmailCertificationRequest{
        private String email;
        private String certificationNumber;
    }


    @Getter
    @AllArgsConstructor
    public static class LoginRequest {

        private String email;
        private String password;

        public static LoginRequest of(String email, String password) {
            return new LoginRequest(email, password);
        }

        public void passwordEncryption(EncryptionService encryptionService) {
            this.password = encryptionService.encrypt(password);
        }
    }
    @Getter
    @AllArgsConstructor
    @Builder
    public static class UserInfoDto {

        private String email;
        private String nickname;
        private String phone;
        private Address address;
        private Account account;
        private boolean emailVerified;

        public static UserInfoDto of(String email, String nickname, String phone, Address address, Account account,boolean emailVerified){
            return new UserInfoDto(email,nickname,phone,address,account,emailVerified);
        }
    }

    @Getter
    @Builder
    public static class FindUserResponse{
        private String email;
        private String phone;
    }

    @Getter
    @AllArgsConstructor
    public static class ChangePasswordRequest{
        private String email;

        @NotBlank(message = "비밀번호를 입력하세요.")
        @Size(min = 8,max = 20,message = "비밀번호는 8자 이상 20자 이하로 입력하세요. ")
        private String passwordAfter;
        private String passwordBefore;

        public void passwordEncryption(EncryptionService encryptionService){
            this.passwordAfter = encryptionService.encrypt(passwordAfter);
            this.passwordBefore = encryptionService.encrypt(passwordBefore);
        }

        public static ChangePasswordRequest of(String email,String passwordAfter,String passwordBefore){
            return new ChangePasswordRequest(email,passwordAfter,passwordBefore);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PasswordRequest{
        private String password;
    }




}
