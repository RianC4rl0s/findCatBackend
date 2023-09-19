package com.catFinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.catFinder.entity.Cat;
import java.util.List;


public interface CatRepository extends JpaRepository<Cat,Integer> {
    public List<Cat> findByUserId(Integer userId);
}
