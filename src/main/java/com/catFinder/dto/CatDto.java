package com.catFinder.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CatDto {
    private Integer id;
    @NotBlank(message = "The post need a title")
    private String title;
    @NotBlank(message = "The post need a cat name")
    private String name;
    private String description;
    @NotNull
    private LocalDateTime date;
    private String picture;
    private double latitude;
    private double longitude;
    @NotNull(message = "user required")
    private UserDto user;
}
