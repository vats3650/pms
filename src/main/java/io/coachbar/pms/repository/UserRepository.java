package io.coachbar.pms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.coachbar.pms.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
