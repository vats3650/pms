package io.coachbar.pms.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import io.coachbar.pms.model.Product;

public interface ProductService {

    Page<Product> getAllProducts(int page, int size, String sortBy, String ascOrDesc);
    Optional<Product> getProductById(String id);
    Product createProduct(Product product);
    Optional<Product> updateProduct(String id, Product updatedProduct);
    boolean deleteProduct(String id);

}
