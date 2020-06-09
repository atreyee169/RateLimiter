package com.blueoptima.ratelimiter.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_CONFIG")
public class UserConfig {

	@Id
	@Column(name = "config_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long configId;
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private Users user;
	private String uri;
	@Column(name = "rate_limit")
	private int rateLimit;
	public long getConfigId() {
		return configId;
	}
	public void setConfigId(long configId) {
		this.configId = configId;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public int getRateLimit() {
		return rateLimit;
	}
	public void setRateLimit(int rateLimit) {
		this.rateLimit = rateLimit;
	}
	
}
