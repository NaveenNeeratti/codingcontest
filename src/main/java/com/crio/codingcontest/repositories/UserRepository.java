package com.crio.codingcontest.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.crio.codingcontest.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User,Integer>{

    User findByUserId(Integer userId);
    
}
