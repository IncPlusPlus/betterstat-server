package io.github.incplusplus.thermostat.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NodeNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public NodeNotFoundException(String message)
	{
		super(message);
	}
}
