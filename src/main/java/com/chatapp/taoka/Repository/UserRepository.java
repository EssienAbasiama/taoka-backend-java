package com.chatapp.taoka.Repository;

import com.chatapp.taoka.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    // Define additional methods if needed (custom queries, etc.)

    Optional<User> findByEmail(String email);




}
