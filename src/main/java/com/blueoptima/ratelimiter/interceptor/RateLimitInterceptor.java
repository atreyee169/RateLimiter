package com.blueoptima.ratelimiter.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.blueoptima.ratelimiter.service.RateLimitService;

@Component
public class RateLimitInterceptor implements HandlerInterceptor{

	@Value("${rate.limit.enabled}")
	private boolean enabled;
	
	@Autowired
	private RateLimitService rateLimitService;
	
	private static final Logger log = LoggerFactory.getLogger(RateLimitInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		/*Check if rate limiter is enabled*/
		if(!enabled)
			return true;
		
		/*Get URI of the API being accessed*/
		String uri=request.getRequestURI().toString();
		
		/*Get current user trying to access the API*/
		String username=null;
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails)
			username=((UserDetails)principal).getUsername();
		
		log.info("User "+username+" requesting uri "+uri);
		
		/*Get Request timestamp*/
		long currentRequestTimestamp=System.currentTimeMillis();
		return rateLimitService.doRateLimit(username, uri, currentRequestTimestamp);
				 
	}
	
}
