package com.labospring.LaboFootApp.pl.advisor;

import com.labospring.LaboFootApp.bll.exceptions.LaboFootException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = extractEnumErrorMessage(ex.getMessage());
        log.error("HTTP Message Not Readable Exception: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }

    private String extractEnumErrorMessage(String fullMessage) {
        // Extraire le message pertinent concernant les erreurs d'enum
        if (fullMessage != null && fullMessage.contains("not one of the values accepted for Enum class")) {
            int index = fullMessage.indexOf("not one of the values accepted for Enum class");
            String enumType = extractEnumType(fullMessage);
            return fullMessage.substring(index).split("\r\n")[0].replace("Enum class", "the " + enumType);
        }
        return "Invalid request payload.";
    }

    private String extractEnumType(String fullMessage) {
        // Extraire le nom de l'enum du message d'erreur
        if (fullMessage != null) {
            int startIndex = fullMessage.indexOf("`com.labospring.LaboFootApp.dl.enums.") + "com.labospring.LaboFootApp.dl.enums.".length();
            int endIndex = fullMessage.indexOf("`", startIndex);
            if (startIndex > 0 && endIndex > startIndex) {
                return fullMessage.substring(startIndex, endIndex);
            }
        }
        return "Enum type";
    }

}
