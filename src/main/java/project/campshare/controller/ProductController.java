package project.campshare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.campshare.domain.service.ProductService;
import project.campshare.dto.ProductDto;

import javax.validation.Valid;

import static project.campshare.util.ResponseConstants.CREATED;
import static project.campshare.util.ResponseConstants.OK;

@RequiredArgsConstructor
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void>createProduct(@Valid@RequestBody ProductDto.SaveRequest request){
        productService.save(request);
        return CREATED;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto.ProductInfoResponse>getProductInfo(@PathVariable Long id){
        ProductDto.ProductInfoResponse response = productService.getProductInfo(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return OK;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void>updateProduct(@PathVariable Long id,
                                             @Valid@RequestBody ProductDto.SaveRequest request){
        productService.updateProduct(id, request);
        return OK;
    }
}
