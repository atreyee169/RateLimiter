package com.blueoptima.ratelimiter;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;

import com.blueoptima.ratelimiter.beans.UserAccess;
import com.blueoptima.ratelimiter.beans.UserConfig;
import com.blueoptima.ratelimiter.beans.Users;
import com.blueoptima.ratelimiter.exception.RateLimitException;
import com.blueoptima.ratelimiter.repository.UserAccessRepo;
import com.blueoptima.ratelimiter.repository.UserConfigRepo;
import com.blueoptima.ratelimiter.repository.UserRepo;
import com.blueoptima.ratelimiter.service.RateLimitService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class TestRatelimiter {

	@InjectMocks
	RateLimitService rateLimitService;	
	@Mock
	private UserAccessRepo userAccessRepo;
	@Mock
	private UserConfigRepo userConfigRepo;
	@Mock
	private UserRepo userRepo;
	
	String username;
	long requestTime;
	String api;
	Users user;
	UserConfig userConfig;
	UserAccess userAccess;
	
	@BeforeEach
	public void init()
	{
		username="mock_user";
		api="/api/test";
		user=new Users();
		userConfig=new UserConfig();
		userAccess=new UserAccess();
	}
	
	@Test
	void testRateLimitRequestAllowRequestsWithinLimit() throws InterruptedException {
		userConfig.setRateLimit(5);

		StringBuilder apiAccessString=new StringBuilder();
		for(int i=0;i<4;i++)
		{
			apiAccessString.append(System.currentTimeMillis()).append(":");
			Thread.sleep(10);
		}
		userAccess.setRequestArr(apiAccessString.toString());

		when(userRepo.findByUsername(username)).thenReturn(user);
		when(userConfigRepo.findByUserAndUri(user, api)).thenReturn(userConfig);
		when(userAccessRepo.findByUserConfig(userConfig)).thenReturn(userAccess);

		requestTime=System.currentTimeMillis();

		assertTrue(rateLimitService.doRateLimit(username, api, requestTime));
	}
	
	@Test
	void testRateLimitRequestAllowRequestsExceedsLimit() throws InterruptedException {
		userConfig.setRateLimit(5);

		StringBuilder apiAccessString=new StringBuilder();
		for(int i=5;i>0;i--)
		{
			apiAccessString.append(System.currentTimeMillis()-(20000*(i+1))).append(":");
			Thread.sleep(10);
		}
		userAccess.setRequestArr(apiAccessString.toString());

		when(userRepo.findByUsername(username)).thenReturn(user);
		when(userConfigRepo.findByUserAndUri(user, api)).thenReturn(userConfig);
		when(userAccessRepo.findByUserConfig(userConfig)).thenReturn(userAccess);
		
		requestTime=System.currentTimeMillis();

		assertTrue(rateLimitService.doRateLimit(username, api, requestTime));
	}
	
	
	  @Test
	  void testRateLimitRequestDeny() throws InterruptedException {
			userConfig.setRateLimit(5);

			StringBuilder apiAccessString=new StringBuilder();
			for(int i=0;i<5;i++)
			{
				apiAccessString.append(System.currentTimeMillis()).append(":");
				Thread.sleep(10);
			}
			userAccess.setRequestArr(apiAccessString.toString());

			when(userRepo.findByUsername(username)).thenReturn(user);
			when(userConfigRepo.findByUserAndUri(user, api)).thenReturn(userConfig);
			when(userAccessRepo.findByUserConfig(userConfig)).thenReturn(userAccess);

			requestTime=System.currentTimeMillis();

			Exception exception=assertThrows(RateLimitException.class, ()->rateLimitService.doRateLimit(username, api, requestTime));
			
			String expectedMsg="Rate limit reached for user:mock_user and uri:/api/test; try later";
			System.out.println(exception.getMessage());
			assertTrue(exception.getMessage().equals(expectedMsg));
		}
	 

}
