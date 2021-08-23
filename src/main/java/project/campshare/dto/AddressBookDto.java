package project.campshare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressBookDto {

    private Long id;
    private String addressName;
    private String roadNameAddress;
    private String detailAddress;
    private String postalCode;

    @Builder
    public AddressBookDto(Long id, String addressName, String roadNameAddress, String detailAddress, String postalCode) {
        this.id = id;
        this.addressName = addressName;
        this.roadNameAddress = roadNameAddress;
        this.detailAddress = detailAddress;
        this.postalCode = postalCode;
    }
}
