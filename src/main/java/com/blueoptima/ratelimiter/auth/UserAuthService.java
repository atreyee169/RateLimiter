package com.blueoptima.ratelimiter.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blueoptima.ratelimiter.beans.AuthGroup;
import com.blueoptima.ratelimiter.beans.Users;
import com.blueoptima.ratelimiter.repository.AuthRepo;
import com.blueoptima.ratelimiter.repository.UserRepo;

@Service
public class UserAuthService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private AuthRepo authRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users user=userRepo.findByUsername(username);
		if(user==null)
			throw new UsernameNotFoundException("Could not find username:"+username);
		
		List<AuthGroup> userAuthList=authRepo.findByUser(user);
		return new UserAuthPrincipal(user, userAuthList);
	}

}
