package io.coachbar.pms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import io.coachbar.pms.exception.InvalidProductIdException;
import io.coachbar.pms.model.Product;
import io.coachbar.pms.service.ProductService;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts_success() {
        Page<Product> page = new PageImpl<>(Collections.emptyList());
        when(productService.getAllProducts(0, 10, "name", "asc")).thenReturn(page);

        Page<Product> result = productController.getAllProducts(0, 10, "name", "asc");

        assertEquals(page, result);
        verify(productService, times(1)).getAllProducts(0, 10, "name", "asc");
    }

    @Test
    void testGetProductById_found() throws InvalidProductIdException {
        Product product = new Product();
        when(productService.getProductById("1")).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.getProductById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(product, response.getBody());
    }

    @Test
    void testGetProductById_notFound() throws InvalidProductIdException {
        when(productService.getProductById("1")).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.getProductById("1");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateProduct_success() {
        Product product = new Product();
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        Product response = productController.createProduct(product);

        assertEquals(product, response);
    }

    @Test
    void testUpdateProduct_found() throws InvalidProductIdException {
        Product product = new Product();
        when(productService.updateProduct(eq("1"), any(Product.class))).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.updateProduct("1", product);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(product, response.getBody());
    }

    @Test
    void testUpdateProduct_notFound() throws InvalidProductIdException {
        Product product = new Product();
        when(productService.updateProduct(eq("1"), any(Product.class))).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.updateProduct("1", product);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteProduct_success() throws InvalidProductIdException {
        when(productService.deleteProduct("1")).thenReturn(true);

        ResponseEntity<Void> response = productController.deleteProduct("1");

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDeleteProduct_notFound() throws InvalidProductIdException {
        when(productService.deleteProduct("1")).thenReturn(false);

        ResponseEntity<Void> response = productController.deleteProduct("1");

        assertEquals(404, response.getStatusCodeValue());
    }

}
