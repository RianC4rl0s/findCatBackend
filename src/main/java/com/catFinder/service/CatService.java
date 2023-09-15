package com.catFinder.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;

import com.catFinder.dto.CatDto;

import com.catFinder.entity.Cat;

import com.catFinder.exception.DataException;
import com.catFinder.exception.UserNotFoundException;
import com.catFinder.repositories.CatRepository;



@Service
public class CatService {

    @Autowired
    CatRepository catRepository;

    @Autowired
    ModelMapper mapper;

    public Cat findById(Integer id) {
        try {
            return catRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("No cat by ID: " + id));

        } catch (UserNotFoundException e) {

            throw new UserNotFoundException("No cat by ID: " + id);
        }
    }

    public List<Cat> findAll() {
        return catRepository.findAll();
    }

    public Page<CatDto> findAllPaged(PageRequest pageRequest) {
        Page<Cat> list = catRepository.findAll(pageRequest);
        return list.map(it -> mapper.map(it, CatDto.class));
    }

    public Cat create(Cat cat) {

        Cat response = catRepository.save(cat);

        return response;

    }

    public Cat update(Integer id, Cat dto) throws Exception {
        Cat c = catRepository.findById(dto.getId()).orElseThrow(() -> new Exception("não foi encontrado"));

        c.setName(dto.getName());
        c.setTitle(dto.getTitle());
        c.setDescription(dto.getDescription());
        c.setLatitude(dto.getLatitude());
        c.setLongitude(dto.getLongitude());
        
        Cat response = catRepository.save(c);

        return response;
    }

    public void delete(Integer id) {

        try {
            Cat cat = catRepository.findById(id).orElseThrow(() -> new UserNotFoundException("cat Not found"));
            catRepository.delete(cat);

        } catch (EmptyResultDataAccessException e) {

            throw new UserNotFoundException("cat not exist");
        } catch (DataIntegrityViolationException e) {
            throw new DataException("Cannot dell cat");
            // throw new Exception("Cannot dell user");
        }

    }

}
