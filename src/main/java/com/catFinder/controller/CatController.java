package com.catFinder.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.catFinder.dto.CatDto;
import com.catFinder.entity.Cat;
import com.catFinder.service.CatService;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cat")
public class CatController {
    
    @Autowired
    CatService catService;

    @Autowired 
    ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<CatDto>> findAll(){
        List<Cat> catList = catService.findAll();
        
        List<CatDto> catDtos = catList.stream().map(cat -> mapper.map(cat, CatDto.class)).collect(Collectors.toList());

        return ResponseEntity.ok().body(catDtos);
    }
    @Transactional
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<CatDto>> findByUserId(@PathVariable("id") Integer id){
        List<Cat> catList = catService.findByUserId(id);
        
        List<CatDto> catDtos = catList.stream().map(cat -> mapper.map(cat, CatDto.class)).collect(Collectors.toList());

        return ResponseEntity.ok().body(catDtos);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Cat> findCatByIdPath(@PathVariable Integer id){
        return ResponseEntity.ok().body(catService.findById(id));
    }

  
    @GetMapping("/paged")
    public ResponseEntity<Page<CatDto>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		Page<CatDto> list = catService.findAllPaged(pageRequest);

		return ResponseEntity.ok().body(list);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid CatDto dto){
        
        Cat response = catService.create(mapper.map(dto, Cat.class));

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(response, CatDto.class));

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid CatDto dto){
        Cat m = mapper.map(dto, Cat.class);
        try {
            catService.update(id, m);
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(m, CatDto.class));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado");
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        catService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Filme deletado com sucesso");
    }

}
