package project.campshare.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.campshare.Model.usermodel.user.User;
import project.campshare.Model.usermodel.user.UserDto;
import project.campshare.Model.usermodel.userservice.SignUpService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserApiController {

    private final SignUpService signUpService;

    @GetMapping
    public List<User> allUsers() {
        return signUpService.findAll();
    }

    @GetMapping("/duplicated/email")
    public boolean emailDuplicated(String email) {
        return signUpService.emailDuplicateCheck(email);
    }

    @GetMapping("/duplicated/nickname")
    public boolean nickNameDuplicated(String nickname) {
        return signUpService.emailDuplicateCheck(nickname);
    }

    @PostMapping("/certification/send")
    public void sendCertificationNumber() {
        signUpService.saveAuthenticationNumber();
    }

    @PostMapping("/certification")
    public ResponseEntity requestCertification(@RequestBody User.CertificationInfo certificationInfo) {
        if (signUpService.certificationNumberInspection(certificationInfo.getNumber())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/new")
    public ResponseEntity signUp(@RequestBody @Valid UserDto signUpDto) {
       User savedUser = signUpService.saveUser(signUpDto);
        URI uri = WebMvcLinkBuilder.linkTo(UserApiController.class).slash(savedUser.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }



}
