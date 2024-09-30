package com.labospring.LaboFootApp.pl.advisor;

import com.labospring.LaboFootApp.bll.exceptions.LaboFootException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(LaboFootException.class)
    public ResponseEntity<String> handleRuntimeException(LaboFootException error){
        log.error(error.toString());
        return ResponseEntity.status(error.getStatus()).body(error.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException error) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", error.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList()));
        errorResponse.put("globalErrors", error.getBindingResult().getGlobalErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
        return ResponseEntity.status(406).body(errorResponse);
    }




}
