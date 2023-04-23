package org.ssglobal.training.codes.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPasswordChange {
	@JsonProperty
	private String email;
	
	@JsonProperty
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
