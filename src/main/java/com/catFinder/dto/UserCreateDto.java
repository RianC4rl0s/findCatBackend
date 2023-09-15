package com.catFinder.dto;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class UserCreateDto {
    Integer id;
    @NotBlank(message = "The name can't be blank")
    @Size(min = 2, message = "{validation.name.size.too_short}")
    @Size(max = 80, message = "{validation.name.size.too_long}")
    private String name;
    @NotBlank(message = "The passoword can't be blank")
    private String password;
    @NotBlank(message = "Username can't be blank")
    private String username;
    @NotBlank(message = "The email can't be blank")
    @Email(message = "Please use a valid email")
    private String email;
}
