package io.coachbar.pms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.coachbar.pms.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
