package com.blueoptima.ratelimiter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.blueoptima.ratelimiter.beans.UserAccess;
import com.blueoptima.ratelimiter.beans.UserConfig;

@Repository
public interface UserAccessRepo extends CrudRepository<UserAccess, Long>{

	public UserAccess findByUserConfig(UserConfig userConfig);
}
