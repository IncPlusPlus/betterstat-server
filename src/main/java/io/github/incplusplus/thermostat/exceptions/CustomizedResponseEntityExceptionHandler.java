package io.github.incplusplus.thermostat.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler
{
	@ExceptionHandler(NodeNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleNotFoundException(NodeNotFoundException exception, WebRequest request)
	{
		ExceptionResponse response = new ExceptionResponse(new Date(), exception.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND.getReasonPhrase());
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
}
