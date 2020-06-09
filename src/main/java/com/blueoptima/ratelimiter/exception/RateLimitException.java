package com.blueoptima.ratelimiter.exception;

public class RateLimitException extends RuntimeException{

	public RateLimitException(String s)
	{
		super(s);
	}
}
