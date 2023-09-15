package com.catFinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.catFinder.entity.Cat;

public interface CatRepository extends JpaRepository<Cat,Integer> {
    
}
