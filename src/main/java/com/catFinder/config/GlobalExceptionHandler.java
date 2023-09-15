package com.catFinder.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.catFinder.exception.ApiErrorMessage;
import com.catFinder.exception.UserNotFoundException;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorMessage> handleValidationErrors(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        System.out.println(request.getRequestURI());
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, errors, Instant.now(),
                "Não foi possivel cadastrar a entidade devio a erros", request.getRequestURI());

        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorMessage> handleNotFoundException(UserNotFoundException ex,
    HttpServletRequest request) {
        System.out.println(request.getRequestURI());
        List<String> errors = Collections.singletonList(ex.getMessage());
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.NOT_FOUND, errors, Instant.now(),
                "Não foi possivel encontrar a entidade devio a erros", request.getRequestURI());

        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
        ex.printStackTrace();
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // @ExceptionHandler(RuntimeException.class)
    // public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException ex) {
    //     List<String> errors = Collections.singletonList(ex.getMessage());
    //     return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // @ExceptionHandler(java.util.NoSuchElementException.class)
    // public final ResponseEntity<Map<String, List<String>>> handleNoSuchElementException(NoSuchElementException ex) {
    //     List<String> errors = Collections.singletonList(ex.getMessage());
    //     return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST
    //     );
    // }
    // @ExceptionHandler(NoHandlerFoundException.class)
    // public final ResponseEntity<Map<String, List<String>>> handlerNNoHandlerException(NoHandlerFoundException  ex) {
    //     List<String> errors = Collections.singletonList(ex.getMessage());
    //     return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST
    //     );
    // }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    // @ExceptionHandler(MethodArgumentNotValidException.class)
    // public ResponseEntity<Map<String, List<String>>>
    // handleValidationErrors(MethodArgumentNotValidException ex,
    // HttpServletRequest request) {
    // System.out.println(request.getRequestURI());
    // List<String> errors = ex.getBindingResult().getFieldErrors()
    // .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
    // return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(),
    // HttpStatus.BAD_REQUEST);
    // }
    
    // @ExceptionHandler(UserNotFoundException.class)
    // public ResponseEntity<Map<String, List<String>>>
    // handleNotFoundException(UserNotFoundException ex,
    // HttpServletRequest request) {
    // System.out.println(request.getRequestURI());
    // List<String> errors = Collections.singletonList(ex.getMessage());
    // return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(),
    // HttpStatus.NOT_FOUND);
    // }
}
