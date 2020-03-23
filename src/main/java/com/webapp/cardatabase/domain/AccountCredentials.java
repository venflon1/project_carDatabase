package com.webapp.cardatabase.domain;

import org.jboss.logging.Logger;

/**
 * @author Roberto
 * 
 * This is a simple POJO class to keep credentials for authentication
 * 
 * */
public class AccountCredentials {

	private static Logger log = Logger.getLogger(AccountCredentials.class);
	
	private String username;
	private String password;
	
	public AccountCredentials() {
		super();
		log.info("constructor AccountCredentials() - START");
		log.info("constructor AccountCredentials() - END");		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}