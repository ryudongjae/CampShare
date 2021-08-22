package project.campshare.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.campshare.domain.model.user.User;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsByEmailAndPassword(String email, String password);
    Optional<User>findByEmail(String email);
    void deleteByEmail(String email);
}
