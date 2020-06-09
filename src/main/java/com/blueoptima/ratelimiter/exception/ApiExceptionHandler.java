package com.blueoptima.ratelimiter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(RateLimitException.class)
	public ResponseEntity<String> handle(RateLimitException exception)
	{
		return new ResponseEntity<String>(exception.getMessage(),HttpStatus.FORBIDDEN);
	}

}
