package project.campshare.domain.model.user.address;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.campshare.dto.AddressBookDto;


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

    public void updateAddressBook(AddressBookDto request){
        address.updateAddress(request);
    }
}
