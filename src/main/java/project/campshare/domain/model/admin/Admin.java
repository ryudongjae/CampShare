package project.campshare.domain.model.admin;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.campshare.domain.model.user.UserBase;
import project.campshare.domain.model.user.UserLevel;

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
