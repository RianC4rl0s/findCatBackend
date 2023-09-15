package com.catFinder.controller;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.catFinder.dto.UserCreateDto;
import com.catFinder.dto.UserDto;
import com.catFinder.entity.User;
import com.catFinder.entity.UserRole;
import com.catFinder.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<User> userList = userService.findAll(); // Obtendo a lista de usuários

        ModelMapper modelMapper = new ModelMapper();

        List<UserDto> userDtoList = userList.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(userDtoList);
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUserByIdPath(@PathVariable Integer id) {
        return ResponseEntity.ok().body(userService.findUserById(id));
    }
    @GetMapping(path = "/username/{username}")
    public ResponseEntity<User> getUserByUsernamePath(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.findByUsername(username));
    }
 
    @GetMapping("/paged")
	public ResponseEntity<Page<UserDto>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		Page<UserDto> list = userService.findAllPaged(pageRequest);

		return ResponseEntity.ok().body(list);
	}
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserCreateDto dto) {
       //ESSA LINHA ABAIXO É um tratamento, que tenho q adaptar
        // if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        UserCreateDto temp = dto;
        temp.setPassword(encryptedPassword);
        User response = mapper.map(temp, User.class);
        response.setRole(UserRole.USER);
        response = userService.create(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(response, UserDto.class));
    }
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid UserCreateDto dto) {
       
        User response = userService.create(mapper.map(dto, User.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(response, UserDto.class));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody @Valid UserDto dto) {
        User u = mapper.map(dto, User.class);
        try {
            userService.update(id, u);
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(u, UserDto.class));
        } catch (Exception e) {
          
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
        
    }

}
