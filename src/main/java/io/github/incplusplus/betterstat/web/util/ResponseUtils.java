package io.github.incplusplus.betterstat.web.util;

import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ResponseUtils {

  public static ResponseEntity<GenericResponse> handleObjectNotFound(
      ObjectNotFoundException exception) {
    GenericResponse response =
        new GenericResponseBuilder()
            .setMessage(
                "Could not find "
                    + exception.getInstanceType().getSimpleName()
                    + " with ID "
                    + exception.getId()
                    + ".")
            .createGenericResponse();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  public static ResponseEntity<GenericResponse> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex) {
    GenericResponse response =
        new GenericResponseBuilder()
            .setMessage("There was an error parsing the request body.")
            .setAdditionalDetails(ex.getMessage())
            .setStackTrace(ex.getStackTrace())
            .createGenericResponse();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  public static ResponseEntity<GenericResponse> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });
    GenericResponse response =
        new GenericResponseBuilder()
            .setMessage(ex.getMessage())
            .setAdditionalDetails(errors)
            .setStackTrace(ex.getStackTrace())
            .createGenericResponse();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }
}
