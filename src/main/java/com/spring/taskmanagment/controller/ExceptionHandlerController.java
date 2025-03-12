package com.spring.taskmanagment.controller;

import com.spring.taskmanagment.exception.RegistrationException;
import com.spring.taskmanagment.exception.ResourceNotFoundException;
import com.spring.taskmanagment.exception.TaskException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<HashMap<String, Object>> resourceNotFound(ResourceNotFoundException e) {

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", e.getMessage());
        response.put("code", HttpStatus.NOT_FOUND.value());
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskException.class)
    public ResponseEntity<HashMap<String, Object>> taskAssignmentHasUserException(TaskException e) {
        return getHashMapResponseEntity(e.getMessage(), e);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<HashMap<String, Object>> userRegisterExistedException(RegistrationException e) {
        return getHashMapResponseEntity(e.getMessage(), e);
    }


    @ExceptionHandler(AuthorizationServiceException.class)
    public ResponseEntity<HashMap<String, Object>> authorizationServiceException(AuthorizationServiceException e) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", e.getMessage());
        response.put("code", HttpStatus.UNAUTHORIZED.value());
        response.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    private ResponseEntity<HashMap<String, Object>> getHashMapResponseEntity(String message, Exception e) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("code", HttpStatus.BAD_REQUEST.value());
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
