package io.coachbar.pms.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import io.coachbar.pms.exception.InvalidProductIdException;
import io.coachbar.pms.model.Product;
import io.coachbar.pms.repository.ProductRepository;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts_success() {
        Page<Product> page = new PageImpl<>(Collections.emptyList());

        when(productRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Product> result = productServiceImpl.getAllProducts(0, 10, "name", "asc");

        assertEquals(page, result);
    }

    @Test
    void getProductById_validId_returnsProduct() throws InvalidProductIdException {
        Product product = new Product();
        when(productRepository.existsById("1")).thenReturn(true);
        when(productRepository.findById("1")).thenReturn(Optional.of(product));

        Optional<Product> result = productServiceImpl.getProductById("1");

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }

    @Test
    void getProductById_invalidId_throwsException() {
        when(productRepository.existsById("1")).thenReturn(false);

        assertThrows(InvalidProductIdException.class, () -> productServiceImpl.getProductById("1"));
    }

    @Test
    void createProduct_success() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        Product result = productServiceImpl.createProduct(product);

        assertEquals(product, result);
    }

    @Test
    void updateProduct_validId_updatesAndReturnsProduct() throws InvalidProductIdException {
        Product existingProduct = new Product();
        existingProduct.setName("OldName");

        Product updatedProduct = new Product();
        updatedProduct.setName("NewName");
        updatedProduct.setDescription("NewDescription");
        updatedProduct.setPrice(BigDecimal.valueOf(99.99));

        when(productRepository.existsById("1")).thenReturn(true);
        when(productRepository.findById("1")).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        Optional<Product> result = productServiceImpl.updateProduct("1", updatedProduct);

        assertTrue(result.isPresent());
        assertEquals("NewName", result.get().getName());
        assertEquals("NewDescription", result.get().getDescription());
        assertEquals(BigDecimal.valueOf(99.99), result.get().getPrice());
    }

    @Test
    void updateProduct_invalidId_throwsException() {
        when(productRepository.existsById("1")).thenReturn(false);

        assertThrows(InvalidProductIdException.class, () -> productServiceImpl.updateProduct("1", new Product()));
    }

    @Test
    void deleteProduct_validId_deletesSuccessfully() throws InvalidProductIdException {
        when(productRepository.existsById("1")).thenReturn(true);
        doNothing().when(productRepository).deleteById("1");

        boolean result = productServiceImpl.deleteProduct("1");

        assertTrue(result);
    }

    @Test
    void deleteProduct_invalidId_throwsException() {
        when(productRepository.existsById("1")).thenReturn(false);

        assertThrows(InvalidProductIdException.class, () -> productServiceImpl.deleteProduct("1"));
    }

}
