package com.blueoptima.ratelimiter.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ACCESS")
public class UserAccess {

	@Id
	@Column(name = "access_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long accessId;
	@OneToOne
	@JoinColumn(name="config_id",nullable = false)
	private UserConfig userConfig;
	@Column(name="request_arr")
	@Lob
	private String requestArr;
	public long getAccessId() {
		return accessId;
	}
	public void setAccessId(long accessId) {
		this.accessId = accessId;
	}
	public UserConfig getUserConfig() {
		return userConfig;
	}
	public void setUserConfig(UserConfig config) {
		this.userConfig = config;
	}
	public String getRequestArr() {
		return requestArr;
	}
	public void setRequestArr(String requestArr) {
		this.requestArr = requestArr;
	}
	
}
