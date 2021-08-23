package project.campshare.domain.model.users.admin;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.campshare.domain.model.users.UserBase;
import project.campshare.domain.model.users.UserLevel;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends UserBase {

    public Admin(String email, String password, UserLevel userLevel) {
        this.email = email;
        this.password =password;
        this.userLevel = userLevel;
    }
}
