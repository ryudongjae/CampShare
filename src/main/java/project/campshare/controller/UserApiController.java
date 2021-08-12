package project.campshare.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.campshare.Model.userlogin.LoginService;
import project.campshare.annotation.CurrentUser;
import project.campshare.annotation.LoginCheck;
import project.campshare.domain.service.UserService;
import project.campshare.domain.service.email.EmailCertificationService;
import project.campshare.domain.service.sms.SmsCertificationService;

import javax.validation.Valid;

import static project.campshare.Model.usermodel.user.UserDto.*;
import static project.campshare.util.ResponseConstants.*;

/**
 * 휴대폰 인증시 전송받은 인증번호를 session에 저장하여 User가 입력한 인증번호화 일치하는지 확인 인증번호 일치 여부에 따라 200 또는 400 반환 회원가입 완료 후
 * 마이페이지로 이동할 수 있는 URI를 HEADER의 Location에 제공하기 위해 HATEOAS 사용
 *
 */

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserApiController {

    private final LoginService loginService;

    private final UserService userService;

    private final SmsCertificationService smsCertificationService;

    private final EmailCertificationService emailCertificationService;

    /**
     * 이메일 검증
     * @param email
     * @return
     */
    @GetMapping("/user-emails/{email}/exists")
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email) {
        return ResponseEntity.ok(userService.emailDuplicateCheck(email));
    }

    /**
     * 닉네임 검증
     * @param nickname
     * @return
     */
    @GetMapping("/user-nicknames/{nickname}/exists")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@PathVariable String nickname) {
        return ResponseEntity.ok(userService.nicknameDuplicateCheck(nickname));
    }

    /**
     * 유저 가입
     * @param requestDto
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody SaveRequest requestDto) {
        userService.saveUser(requestDto);
        return CREATED;
    }

    /**
     * 인증번호 전송
     * @param requestDto
     * @return
     */
    @PostMapping("/sms-certification/sends")
    public ResponseEntity<Void> sendSms(@RequestBody SmsCertificationRequest requestDto) {
        smsCertificationService.sendSms(requestDto.getPhone());
        return CREATED;
    }

    /**
     * 인증번호 확인
     * @param requestDto
     * @return
     */
    @PostMapping("/sms-certification/confirms")
    public ResponseEntity<Void> SmsVerification(@RequestBody SmsCertificationRequest requestDto) {
        if (!smsCertificationService.verifySms(requestDto)) {
            return BAD_REQUEST;
        }
        return OK;
    }

    /**
     * 로그인
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        loginService.existByEmailAndPassword(loginRequest);
        loginService.login(loginRequest.getEmail());
        return OK;
    }

    /**
     * 로그 아웃
     * @return
     */
    @LoginCheck
    @DeleteMapping("/logout")
    public ResponseEntity logout() {
        loginService.logout();
        return OK;
    }

    /**
     * 내 정보
     * @param email
     * @return
     */
    @LoginCheck
    @GetMapping("/my-infos")
    public ResponseEntity<UserInfoDto> myPage(@CurrentUser String email) {
        UserInfoDto loginUser = loginService.getCurrentUser(email);
        return ResponseEntity.ok(loginUser);
    }
    //비밀번호 찾기 : 이메일 입력시, 존재하는 이메일이면 휴대폰인증과 이메일인증 중 택1 하도록 구현
    //휴대폰 인증 선택시 : sendSms / SmsVerification 핸들러
    //이메일 인증 선택시 : sendEmail / emailVerification 핸들러


    @GetMapping("/find/password/{email}")
    public ResponseEntity<FindUserRequest> forgot_password(@PathVariable String email){
        FindUserRequest findUserRequest = userService.getPhoneNumber(email);
        return ResponseEntity.ok(findUserRequest);
    }


    @PostMapping("/email-certification/sends")
    public ResponseEntity sendEmail(@RequestBody EmailCertificationRequest request){
        emailCertificationService.sendEmail(request.getEmail());
        return CREATED;
    }

    @PostMapping("/email-certification/confirms")
    public ResponseEntity emailVerification(@RequestBody EmailCertificationRequest request){
        if(!emailCertificationService.verifyEmail(request)){
            return BAD_REQUEST;
        }
        return OK;
    }


    @PatchMapping("password-nonLogin")
    public ResponseEntity changePassword(@RequestBody ChangePasswordRequest request){
        userService.updatePassword(request);
        return OK;
    }
}