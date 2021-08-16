package project.campshare.Model.usermodel.user.address;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.campshare.Model.usermodel.user.UserDto.ChangeAddressRequest;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressBook {

    @Id@GeneratedValue
    private Long id;

    @Embedded
    private Address address;

    @Builder
    public AddressBook(Address address) {
        this.address = address;
    }

    public void updateAddressBook(ChangeAddressRequest request){
        address.updateAddress(request);
    }
}
