package com.catFinder.exception;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorMessage {
    private HttpStatus status;
    private List<String> errors;
    private Instant timestamp;
    private String message;
    private String path;

    public ApiErrorMessage(HttpStatus status, List<String> errors, Instant timestamp, String message, String path) {
        super();
        this.status = status;
        this.errors = errors;
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
    }

    public ApiErrorMessage(HttpStatus status, String error, Instant timestamp, String message, String path) {
        super();
        this.status = status;
        errors = Arrays.asList(error);
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
    }
}