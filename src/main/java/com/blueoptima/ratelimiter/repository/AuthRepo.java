package com.blueoptima.ratelimiter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.blueoptima.ratelimiter.beans.AuthGroup;
import com.blueoptima.ratelimiter.beans.Users;

@Repository
public interface AuthRepo extends CrudRepository<AuthGroup, Long> {

	List<AuthGroup> findByUser(Users user);
}
