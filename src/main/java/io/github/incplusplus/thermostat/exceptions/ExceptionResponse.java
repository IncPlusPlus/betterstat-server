package io.github.incplusplus.thermostat.exceptions;

import java.util.Date;

public class ExceptionResponse
{
	private Date timestamp;
	private String message;
	private String details;
	private String httpStatusCode;
	
	public ExceptionResponse(Date timestamp, String message, String details, String httpStatusCode)
	{
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
		this.httpStatusCode = httpStatusCode;
	}
	
	public String getHttpStatusCode()
	{
		return httpStatusCode;
	}
	
	public Date getTimestamp()
	{
		return timestamp;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public String getDetails()
	{
		return details;
	}
	
}