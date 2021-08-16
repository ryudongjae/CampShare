package project.campshare.Model.usermodel.user;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AddressBook {

    @Id@GeneratedValue
    private Long id;

    @Embedded
    private Address address;
}
