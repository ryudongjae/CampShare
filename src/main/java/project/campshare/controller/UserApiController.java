package project.campshare.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.campshare.Model.usermodel.user.UserDto;
import project.campshare.Model.usermodel.userservice.CertificationService;
import project.campshare.Model.usermodel.userservice.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserApiController {

    private final UserService userService;
    private final CertificationService certificationService;

    /**
     * 이메일 중복 확인
     * @param email
     * @return
     */
    @GetMapping("/duplication/email")
    public ResponseEntity<Boolean>checkEmailDuplicate(@RequestParam String email){
        return ResponseEntity.ok(userService.checkEmailUnique(email));
    }

    /**
     * 닉네임 중복 확인
     * @param nickname
     * @return
     */
    @GetMapping("/duplication/nickname")
    public ResponseEntity<Boolean>checkNickNameDuplicate(@RequestParam String nickname){
        return ResponseEntity.ok(userService.checkEmailUnique(nickname));
    }

    /**
     * 유저 등록
     * @param requestDto
     * @return
     */
    @PostMapping
    public ResponseEntity<Void>createUser(@Valid @RequestParam UserDto.SaveRequest requestDto){
        userService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 문자 인증
     * @param map
     * @return
     */
    @PostMapping("/certification/sms")
    public ResponseEntity<Void> sendSms(@RequestBody Map<String, String> map) {
        System.out.println(map.get("phone"));
        certificationService.sendSms(map.get("phone"));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 인증번호가 동일한지 체크
     * @param requestDto
     * @return
     */
    @PostMapping("/certification/verification")
    public ResponseEntity<Void> phoneVerification(@RequestParam UserDto.CertificationRequest requestDto) {
        if (!certificationService.phoneVerification(requestDto))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.OK).build();
    }




}
