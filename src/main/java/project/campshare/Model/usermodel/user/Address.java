package project.campshare.Model.usermodel.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private String addressName; //주소명
    private String roadAddress; //도로명주소
    private String detailAddress; //상세주소
    private String postalCode; //우편번호
}
