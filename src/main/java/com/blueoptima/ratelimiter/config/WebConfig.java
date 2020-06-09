package com.blueoptima.ratelimiter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.blueoptima.ratelimiter.interceptor.RateLimitInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Autowired
	RateLimitInterceptor rateLimitInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(rateLimitInterceptor).addPathPatterns("/api/**");//.order(Integer.MAX_VALUE);
	}

	
}
