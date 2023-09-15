package com.catFinder.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.catFinder.entity.User;



public interface UserRepository extends JpaRepository<User,Integer> {
    public Optional<User> findByUsername(String username);

}
