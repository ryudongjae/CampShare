package project.campshare.Model.usermodel.user.address;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.campshare.Model.usermodel.user.UserDto.ChangeAddressRequest;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Address {
    private String addressName; //주소명
    private String roadAddress; //도로명주소
    private String detailAddress; //상세주소
    private String postalCode; //우편번호

    public void updateAddress(ChangeAddressRequest request) {
        this.addressName = request.getAddressName();
        this.roadAddress = request.getRoadNameAddress();
        this.detailAddress = request.getDetailedAddress();
        this.postalCode = request.getPostalCode();
    }
}
