package io.coachbar.pms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.coachbar.pms.exception.InvalidProductIdException;
import io.coachbar.pms.model.Product;
import io.coachbar.pms.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/products")
@Tag(name = "Product API", description = "CRUD operations for Products")
public class ProductController {

    Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController (ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all product pages")
    public Page<Product> getAllProducts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String ascOrDesc) {
        LOGGER.info("Received get all product request");
        return productService.getAllProducts(page, size, sortBy, ascOrDesc);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<Product> getProductById(@PathVariable String id) throws InvalidProductIdException {
        LOGGER.info("Received get request for product id: "+ id);
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new Product")
    @PreAuthorize("hasRole('ADMIN')")
    public Product createProduct(@RequestBody Product product) {
        LOGGER.info("Received request for new product creation");
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing Product by ID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) throws InvalidProductIdException {
        LOGGER.info("Update request received for product id: " + id);
        return productService.updateProduct(id, product)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete exisitng Product by ID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) throws InvalidProductIdException {
        LOGGER.info("Delete request received for product id: " + id);
        return productService.deleteProduct(id) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

}
