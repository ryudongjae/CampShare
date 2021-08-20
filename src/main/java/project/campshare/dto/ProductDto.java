package project.campshare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.campshare.domain.model.product.Product;
import project.campshare.domain.model.product.ProductState;
import project.campshare.domain.model.product.TransactionMethod;
import project.campshare.domain.model.product.TransactionStatus;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductDto {

    @Getter
    @NoArgsConstructor
    public static class SaveRequest{
        @NotBlank(message = "제품명을 입력해주세요.")
        private String name;

        @NotBlank(message = "판매 가격을 입력해주세요.")
        private String salePrice;

        @NotBlank(message = "제품에 대한 설명을 입력해주세요.")
        @Size(max = 200,message = "200자 이내로 입력해주세요.")
        private String productDescription;

        private String releasePrice;

        @NotNull(message = "")
        private TransactionStatus transactionStatus;

        @NotNull(message = "제품의 상태를 선택해주세요.")
        private ProductState productState;

        @NotNull(message = "제품 거래 방식을 선택해주세요.")
        private TransactionMethod transactionMethod;
        public Product toEntity(){
            return Product.builder()
                    .name(this.name)
                    .salePrice(this.salePrice)
                    .productDescription(this.productDescription)
                    .releasePrice(this.releasePrice)
                    .transactionStatus(this.transactionStatus)
                    .productState(this.productState)
                    .transactionMethod(this.transactionMethod)
                    .build();
        }

    }
    @Getter
    @AllArgsConstructor
    @Builder
    public static class ProductInfoResponse{
        private Long id;

        private String name;

        private String salePrice;

        private String productDescription;

        private String releasePrice;

        private TransactionStatus transactionStatus;

        private ProductState productState;


        private TransactionMethod transactionMethod;
    }

}
