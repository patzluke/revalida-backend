package org.ssglobal.training.codes;

import java.util.Properties;

public class MySecretKey {
	private final String SECRET_KEY = "r3vaLid@";
	public String getSecretKey() {
		Properties prop = new Properties();
		prop.setProperty("secret.userkey", SECRET_KEY);
		return prop.getProperty("secret.userkey");
	}
}
