package com.webapp.cardatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * @author Roberto
 * 
 * Default behaviour of Spring security is :
 *
 * 		-> THE CREATION OF USER IN MEMORY WITH:
 * 				-> username: user
 * 				-> password: is logged in console when application is started
 * 
 * This class serve for configure (overrides) the default behaviour of Spring security.
 * 
 * --------------------------------------------------------------------------------------------
 * 
 * NOTE_1: This class must be located in root package, so in the same level
 * 		   of Application class that is a class that contains main method
 * 
 * NOTE_2: The annotations:
 * 
 * 				-> @Configuration 
 * 				-> @EnableWebSecurity
 * 
 * 			allow us to switch of the default behavior of spring security
 * 
 * NOTE_2: In this class we must ovverdide a method that have the follow signature:
 * 	
 * 				-> protected void configure(HttpSecurity http) throws Exception
 * 
 * 			This override allow us to define a new custom configuration for spring security.
 * 			Instead we define the specific endpoint that we render secure.
 * 			If we want render secure all endpoint of our application, then
 * 			in the override method we don't write anything
 * 
 * */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static Logger log = LoggerFactory.getLogger(SecurityConfig.class);

	
	/**
	 * 
	 * In this method we define a custom configuration for spring security
	 * 
	 * */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("configure(HttpSecurity http) - START");
		log.info("configure(HttpSecurity http) - DEBUG: \n\n\tparam: {\n\t\t  http: " + http.toString() + "\n\t\t}\n");
		
		super.configure(http);
		
		log.info("configure(HttpSecurity http) - END");
	}
	
	
}
