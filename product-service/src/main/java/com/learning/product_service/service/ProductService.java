package com.learning.product_service.service;

import com.learning.product_service.dto.ProductRequest;
import com.learning.product_service.dto.ProductResponse;
import com.learning.product_service.model.Product;
import com.learning.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        log.info("Product saved: " + productRequest.toString());
    }

    public List<ProductResponse> getAllproducts(){
        return productRepository.findAll().stream().map(product -> ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build()
        ).toList();
    }
}
