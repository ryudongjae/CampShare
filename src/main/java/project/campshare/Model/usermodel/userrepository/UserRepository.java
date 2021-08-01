package project.campshare.Model.usermodel.userrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.campshare.Model.usermodel.user.User;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existByEmail(String email);
    boolean existByUserName(String username);

}
