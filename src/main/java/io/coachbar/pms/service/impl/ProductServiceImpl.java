package io.coachbar.pms.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import io.coachbar.pms.exception.InvalidProductIdException;
import io.coachbar.pms.model.Product;
import io.coachbar.pms.repository.ProductRepository;
import io.coachbar.pms.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    public ProductServiceImpl (ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> getAllProducts(int page, int size, String sortBy, String ascOrDesc) {
        Sort sort = Sort.by(Sort.Direction.fromString(ascOrDesc), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> getProductById(String id) throws InvalidProductIdException {
        try {
            validateId(id);
        } catch (InvalidProductIdException e) {
            throw e;
        }
        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> updateProduct(String id, Product updatedProduct) throws InvalidProductIdException {
        try {
            validateId(id);
        } catch (InvalidProductIdException e) {
            throw e;
        }
        return productRepository.findById(id).map(existing -> {
            existing.setName(updatedProduct.getName());
            existing.setDescription(updatedProduct.getDescription());
            existing.setPrice(updatedProduct.getPrice());
            return productRepository.save(existing);
        });
    }

    @Override
    public boolean deleteProduct(String id) throws InvalidProductIdException {
        try {
            validateId(id);
        } catch (InvalidProductIdException e) {
            throw e;
        }
        productRepository.deleteById(id);
        return true;
    }

    private void validateId(String id) throws InvalidProductIdException {
        if(id==null) {
            LOGGER.error("id is null");
            throw new InvalidProductIdException("product id is not valid");
        } else if(!productRepository.existsById(id)) {
            LOGGER.error("product with given id is not there");
            throw new InvalidProductIdException("no product found for given id");
        }
    }

}
