package project.campshare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.campshare.annotation.LoginCheck;
import project.campshare.domain.model.users.UserLevel;
import project.campshare.domain.service.AdminService;
import project.campshare.domain.repository.UserRepository;
import project.campshare.dto.UserDto;
import project.campshare.dto.UserDto.SaveRequest;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminService adminService;
    private final UserRepository userRepository;

    @GetMapping("/temp")
    public void addUser() {
        for (int i = 0; i < 30; i++) {
            SaveRequest saveRequest = SaveRequest
                    .builder()
                    .email("rrr1111@naver.com" + i)
                    .nickname("test1234" + i)
                    .phone("01022124455")
                    .password("test1232")
                    .build();
        }
    }

    @LoginCheck(authority = UserLevel.ADMIN)
    @GetMapping("/users")
    public Page<UserDto.UserListResponse> findByUsers(UserDto.UserSearchCondition requestDto, Pageable pageable) {
        return adminService.findUsers(requestDto, pageable);
    }
}