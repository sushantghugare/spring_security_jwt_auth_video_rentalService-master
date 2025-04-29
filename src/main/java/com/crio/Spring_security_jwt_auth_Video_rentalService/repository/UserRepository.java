package com.crio.Spring_security_jwt_auth_Video_rentalService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crio.Spring_security_jwt_auth_Video_rentalService.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

}
