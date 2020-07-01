package io.github.incplusplus.betterstat.web.exception;

import io.github.incplusplus.betterstat.web.util.GenericResponse;
import io.github.incplusplus.betterstat.web.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionsHandler {
  @ExceptionHandler(ObjectNotFoundException.class)
  ResponseEntity<GenericResponse> handleObjectNotFoundException(
      ObjectNotFoundException exception, WebRequest webRequest) {
    return ResponseUtils.handleObjectNotFound(exception);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  protected ResponseEntity<GenericResponse> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex, WebRequest request) {
    return ResponseUtils.handleHttpMessageNotReadable(ex);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<GenericResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    return ResponseUtils.handleMethodArgumentNotValid(ex);
  }
}
