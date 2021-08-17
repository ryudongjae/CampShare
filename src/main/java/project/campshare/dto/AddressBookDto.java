package project.campshare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressBookDto {

    private Long id;
    private String addressName;
    private String roadNameAddress;
    private String detailAddress;
    private String postalCode;
}
