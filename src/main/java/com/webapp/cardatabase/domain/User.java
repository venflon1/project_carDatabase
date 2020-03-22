package com.webapp.cardatabase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Roberto
 * 
 * */
@Entity
public class User {

	// Specifica che la proprietà o il campo non è persistente. 
	// Viene utilizzato per annotare una proprietà o un campo di una classe di entità, superclasse mappata o classe incorporabile.
	@Transient
	private Logger log = LoggerFactory.getLogger(Car.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;
	
	@Column(name = "USERNAME", nullable = false, unique = true)
	private String username;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	@Column(name = "ROLE", nullable = false)
	private String role;
	
	
	public User(String username, String password, String role) {
		super();
		log.info("constructor User(String username, String password, String role) - START");

		this.username = username;
		this.password = password;
		this.role = role;
		log.info("constructor User(String username, String password, String role) - DEBUG: \nstate: " +
				"{\n" + 
					"\t id: " +  	    this.id + "\n" +
					"\t username: " +   this.username + "\n" +
					"\t password: " +  	this.password + "\n" +
					"\t role: " +  		this.role + "\n" +
				"}");
		
		log.info("constructor User(String username, String password, String role) - END");
	}
	
	public User() {
		this(null, null, null);
		log.info("constructor User() - END");

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + "]";
	}
}
