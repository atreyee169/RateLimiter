package com.blueoptima.ratelimiter.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ACCESS")
public class UserAccess {

	@Id
	private Long id;
	@OneToOne
	@JoinColumn(name="config_id",nullable = false)
	@MapsId
	private UserConfig userConfig;
	@Column(name="request_arr")
	@Lob
	private String requestArr;
	
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
