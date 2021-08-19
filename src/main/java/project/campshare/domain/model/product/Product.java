package project.campshare.domain.model.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.campshare.domain.model.usermodel.user.BaseTimeEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Product extends BaseTimeEntity {

    @Id@GeneratedValue
    private Long id;

    private String name;

    private String salePrice;

    private String productDescription;

    private String releasePrice;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Enumerated(EnumType.STRING)
    private ProductState productState;

    //거래방식

    @Builder

    public Product(Long id, String name, String salePrice, String productDescription,
                   String releasePrice, TransactionStatus transactionStatus, ProductState productState) {
        this.id = id;
        this.name = name;
        this.salePrice = salePrice;
        this.productDescription = productDescription;
        this.releasePrice = releasePrice;
        this.transactionStatus = transactionStatus;
        this.productState = productState;
    }
}
