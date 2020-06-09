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
@Table(name = "AUTHORITIES")
public class AuthGroup {

	@Column(name = "auth_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long authId;
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private Users user;
	private String authority;
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	
}
