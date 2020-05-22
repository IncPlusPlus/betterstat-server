package io.github.incplusplus.betterstat.web.util;

import io.github.incplusplus.betterstat.web.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

  public static ResponseEntity<GenericResponse> handleObjectNotFoundException(
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
}
