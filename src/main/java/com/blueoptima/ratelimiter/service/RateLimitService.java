package com.blueoptima.ratelimiter.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.blueoptima.ratelimiter.beans.UserAccess;
import com.blueoptima.ratelimiter.beans.UserConfig;
import com.blueoptima.ratelimiter.beans.Users;
import com.blueoptima.ratelimiter.exception.RateLimitException;
import com.blueoptima.ratelimiter.repository.UserAccessRepo;
import com.blueoptima.ratelimiter.repository.UserConfigRepo;
import com.blueoptima.ratelimiter.repository.UserRepo;

@Service
@Transactional(isolation=Isolation.REPEATABLE_READ)
public class RateLimitService {

	@Autowired
	private UserAccessRepo userAccessRepo;
	@Autowired
	private UserConfigRepo userConfigRepo;
	@Autowired
	private UserRepo userRepo;
	
	private static final Logger log = LoggerFactory.getLogger(RateLimitService.class);
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public boolean doRateLimit(String username,String uri,long currentReqTs)
	{
		/*Get user object for username*/
		Users user=userRepo.findByUsername(username);		
		
		/*Get rate limit config for the logged in User and the API*/
		UserConfig userConfig=userConfigRepo.findByUserAndUri(user, uri);
		if(userConfig==null)
			throw new RateLimitException("No rate limit config found for user:"+username+" and uri:"+uri);
			
		/*Get rate limit value for the user and api combination*/
		long apiRateLimit=userConfig.getRateLimit();
		
		/*Get past request details for the User and API*/
		UserAccess userAccess=userAccessRepo.findByUserConfig(userConfig);
		
		if(userAccess==null)
		{
			userAccess=new UserAccess();
			userAccess.setUserConfig(userConfig);
			userAccess.setRequestArr(System.currentTimeMillis()+":");
			userAccessRepo.save(userAccess);
			return true;
		}
		
		Set<String> userAccessTimeSet=new LinkedHashSet<String>(Arrays.asList(userAccess.getRequestArr().split(":")));
		System.out.println(userAccessTimeSet);
		
		/*Check if incoming request is within rate limit*/
		if(!isReqestValid(userAccessTimeSet, currentReqTs,apiRateLimit))
			throw new RateLimitException("Rate limit reached for user:"+username+" and uri:"+uri+"; try later");
		
		 /*Check if request threshold is reached*/
		userAccess.setRequestArr(userAccessTimeSet.stream().collect(Collectors.joining(":")));
		userAccessRepo.save(userAccess);
		return true;
	}
	
	private boolean isReqestValid(Set<String> apiAccessArr,long currentReqTs,long apiRateLimit)
	{
		log.info("Current request arr size:"+apiAccessArr.size()+";"+apiAccessArr);
		Set<String> requestTimestampSet=new LinkedHashSet<String>(apiAccessArr);
		
		/* If user api timestamp list is less than the api rate limit, allow this request*/
		if(apiAccessArr.size()<apiRateLimit)
		{
				apiAccessArr.add(currentReqTs+"");
				log.info("Updated request arr size:"+apiAccessArr.size()+";"+apiAccessArr);
				return true;
		}
		/* If user api timmestamp list has already reached the api rate limit then try to delete entries older than a minute*/
		/*Remove requests older than a minute*/
		for(String str:requestTimestampSet)
		{
			long prevReqtime=Long.parseLong(str);
			long interval=(currentReqTs-prevReqtime)/(1000*60);
			if(interval>1)
				apiAccessArr.remove(str);
			else break;/* If one of the older intervals is not greaer than a minute then the newer ones will also not be greater than a minute;
			so no need to chk further*/
		}
				
		if(apiAccessArr.size()>=apiRateLimit)
			return false;
			
		apiAccessArr.add(currentReqTs+"");
		log.info("Updated request arr size:"+apiAccessArr.size()+";"+apiAccessArr);
		return true;
	}
}
