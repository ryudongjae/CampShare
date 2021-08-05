package project.campshare.controller;



import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.campshare.Model.usermodel.user.User;
import project.campshare.Model.usermodel.user.UserDto;
import project.campshare.domain.service.SignUpService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static project.campshare.util.ResponseConstants.RESPONSE_BAD_REQUEST;
import static project.campshare.util.ResponseConstants.RESPONSE_OK;

/**
 * 휴대폰 인증시 전송받은 인증번호를 session에 저장하여 User가 입력한 인증번호화 일치하는지 확인 인증번호 일치 여부에 따라 200 또는 400 반환 회원가입 완료 후
 * 마이페이지로 이동할 수 있는 URI를 HEADER의 Location에 제공하기 위해 HATEOAS 사용
 *
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")//공통 매핑 주소
public class UserApiController {

    private final SignUpService signUpService;

    /**
     * 유저목록
     * @return
     */
    @GetMapping
    public List<User> allUsers() {
        return signUpService.findAll();
    }

    /**
     * 이메일 중복 체크
     * @param email
     * @return
     */
    @GetMapping("/user-email/{email}/exist")
    public boolean emailDuplicated(@PathVariable String email) {
        return signUpService.emailDuplicateCheck(email);
    }

    /**
     * 닉네임 중복 체크
     * @param nickname
     * @return
     */
    @GetMapping("/user-nickname/{nickname}/exist")
    public boolean nickName(@PathVariable String nickname) {
        return signUpService.emailDuplicateCheck(nickname);
    }

    /**
     * 인증 번호 전송
     */
    @PostMapping("/certification/send")
    public void sendCertificationNumber(@RequestBody UserDto.CertificationInfo certificationInfo) {
        signUpService.saveAuthenticationNumber(certificationInfo.getPhoneNumber());
    }

    /**
     * 인증번호 검증
     * @param certificationInfo
     * @return
     */
    @PostMapping("/certification")
    public ResponseEntity requestCertification(@RequestBody UserDto.CertificationInfo certificationInfo) {
        if (signUpService.certificationNumberInspection(certificationInfo.getPhoneNumber())){
         return RESPONSE_OK;
        }

        return RESPONSE_BAD_REQUEST;
    }

    /**
     * 회원가입
     * @param signUpDto
     * @return
     */
    @PostMapping
    public ResponseEntity signUp(@RequestBody @Valid UserDto signUpDto) {
       User savedUser = signUpService.saveUser(signUpDto);
        URI uri = WebMvcLinkBuilder.linkTo(UserApiController.class).slash(savedUser.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }



}
