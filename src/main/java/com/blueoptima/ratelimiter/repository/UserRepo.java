package com.blueoptima.ratelimiter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.blueoptima.ratelimiter.beans.Users;

@Repository
public interface UserRepo extends CrudRepository<Users, Long>{

	Users findByUsername(String username);
}
