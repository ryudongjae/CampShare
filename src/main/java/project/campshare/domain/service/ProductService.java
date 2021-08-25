package project.campshare.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.campshare.domain.model.product.Product;
import project.campshare.domain.repository.ProductRepository;
import project.campshare.dto.ProductDto;
import project.campshare.dto.ProductDto.ProductInfoResponse;
import project.campshare.exception.product.ProductNotFoundException;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public void save(ProductDto.SaveRequest request){
        productRepository.save(request.toEntity());
    }

    @Cacheable(value = "product",key = "#id")
    public ProductInfoResponse getProductInfo(Long id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException())
                .toProductInfoResponse();
    }
    @Cacheable(value = "product",key = "#id")
    public void deleteProduct(Long id){
        if(!productRepository.existsById(id)){
            throw new ProductNotFoundException();
        }
        productRepository.deleteById(id);
    }

    @Cacheable(value = "product",key = "#id")
    @Transactional
    public void updateProduct(Long id, ProductDto.SaveRequest request){
        Product saveProduct = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException());

        saveProduct.update(request);
    }
}
