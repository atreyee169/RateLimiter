package com.blueoptima.ratelimiter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.blueoptima.ratelimiter.beans.UserConfig;
import com.blueoptima.ratelimiter.beans.Users;

@Repository
public interface UserConfigRepo extends CrudRepository<UserConfig, Long>{

	public UserConfig findByUserAndUri(Users user,String uri);
}
