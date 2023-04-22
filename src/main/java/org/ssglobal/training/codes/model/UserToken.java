package org.ssglobal.training.codes.model;

public class UserToken {

	private Integer userId;
	private String token;
	
	public UserToken() { }
	
	public UserToken(Integer userId, String token) {
		super();
		this.userId = userId;
		this.token = token;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
