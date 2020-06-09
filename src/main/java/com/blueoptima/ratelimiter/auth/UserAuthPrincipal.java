package com.blueoptima.ratelimiter.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.blueoptima.ratelimiter.beans.AuthGroup;
import com.blueoptima.ratelimiter.beans.Users;

public class UserAuthPrincipal implements UserDetails{

	private Users user;
	private List<AuthGroup> authgrp;
	
	
	public UserAuthPrincipal(Users user, List<AuthGroup> authgrp) {
		super();
		this.user = user;
		this.authgrp = authgrp;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		if(authgrp==null)
			return Collections.EMPTY_SET;
		Set<SimpleGrantedAuthority> grantedAuthorities=new HashSet<SimpleGrantedAuthority>();
		authgrp.forEach(grp->grantedAuthorities.add(new SimpleGrantedAuthority(grp.getAuthority())));
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
