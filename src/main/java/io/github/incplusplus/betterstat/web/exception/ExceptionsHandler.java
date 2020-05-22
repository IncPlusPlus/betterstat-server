package io.github.incplusplus.betterstat.web.exception;

import io.github.incplusplus.betterstat.web.util.GenericResponse;
import io.github.incplusplus.betterstat.web.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(ObjectNotFoundException.class)
  ResponseEntity<GenericResponse> handleObjectNotFoundException(
      ObjectNotFoundException exception, WebRequest webRequest) {
    return ResponseUtils.handleObjectNotFoundException(exception);
  }
}
