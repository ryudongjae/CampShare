package project.campshare.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.campshare.domain.repository.UserRepository;
import project.campshare.dto.AdminDto;
import project.campshare.dto.UserDto;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserRepository userRepository;

    public Page<UserDto.UserListResponse> findUsers(UserDto.UserSearchCondition request, Pageable pageable){
        return  userRepository.searchByUsers(request,pageable);
    }
}
