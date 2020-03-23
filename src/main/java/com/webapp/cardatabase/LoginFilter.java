package com.webapp.cardatabase;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.cardatabase.domain.AccountCredentials;
import com.webapp.cardatabase.service.AuthenticationService;

/**
 * This is a filter class for login and authentication.
 * 
 * NOTE_1: this class must be located in the root package, so in the same level of Application class that contains main method
 * 
 * NOTE_2: this class extends AbstractAuthenticationProcessingFilter, so is more specific than parent class and ovveride two methods from parent class
 * 
 * 
 * NOTE_3: Authentication is performed by the methode called: --> 'attemptAuthentication' (core method for authentication)
 * 				
 * 				NOTE --> IF AUTHENTICATION IS SUCCESFULL, THEN another method called 'succesfullAuthentications' is performed
 * 
 * 
 * VERY IMPORTANT: The LoginFilter (this) class extends the
 *		           Spring Security AbstractAuthenticationProcessingFilter, which REQUIRES (is need) that we setting
 *      	       the authenticationManager property.
 * */
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

	private static Logger log = Logger.getLogger(LoginFilter.class);
	
	public LoginFilter(String url, AuthenticationManager authManager) {
		super( new AntPathRequestMatcher(url));
		log.info("LoginFilter(String url, AuthenticationManager authManager) - START");
		log.info("LoginFilter(String url, AuthenticationManager authManager) - INPUT_PARAM_1: \n\n\t{\n\t\t url: " + url +"\n}\n\n");
		log.info("LoginFilter(String url, AuthenticationManager authManager) - INPUT_PARAM_2: \n\n\t{\n\t\t authManager: " + authManager +"\n}\n\n");
		
		// setting the authenticationManager property passed from param of the constructor
		// this method (setAuthenticationManager) is inherit by parent class
		this.setAuthenticationManager(authManager);
		
		log.info("LoginFilter(String url, AuthenticationManager authManager) - END");
	}

	
	/**
	 * This method perfome the Authentication process
	 * 
	 * IF AUTHENTICATION IS SUCCESFULL, THEN succesfullAuthentications method is performed OTHERWISE succesfullAuthentications method not is performed.
	 * The succesfullAuthentications method then call addToken method 
	 * 
	 * */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		log.info("attemptAuthentication(HttpServletRequest request, HttpServletResponse response) - START");
		log.info("attemptAuthentication(HttpServletRequest request, HttpServletResponse response) - INPUT_PARAM_1: \n\n\t{\n\t\t request: " + request +"\n}\n\n");
		log.info("attemptAuthentication(HttpServletRequest request, HttpServletResponse response) - INPUT_PARAM_2: \n\n\t{\n\t\t response: " + response +"\n}\n\n");
		
		// read value from request (JSON format) and transform payload (that contains a JSON with username and psw) 
		// in Java Object of type AccountCredentials (that is my custom pojo that i had create before)
		AccountCredentials credentials = new ObjectMapper()
												.readValue(request.getInputStream(), AccountCredentials.class);
		log.info("attemptAuthentication(HttpServletRequest request, HttpServletResponse response) - DEBUG:");
		log.info("");
		
		// UsernamePasswordAuthenticationToken is a class of SPring that manage Authentication Username and Password that are need for authentication
		// create an object of type UsernamePasswordAuthenticationToken passing the username, password and Role of my pojo AccountCredntials
		UsernamePasswordAuthenticationToken usernameAndPasswordAuthToken = new UsernamePasswordAuthenticationToken( credentials.getUsername(), 
																												    credentials.getPassword(),																												    
																												    Collections.emptyList() );
		log.info("attemptAuthentication(HttpServletRequest request, HttpServletResponse response) - DEBUG:");
		
		// finally Authentication object is built 
		Authentication auth = this.getAuthenticationManager()
					.authenticate(usernameAndPasswordAuthToken);
		log.info("attemptAuthentication(HttpServletRequest request, HttpServletResponse response) - DEBUG:");
		
		log.info("attemptAuthentication(HttpServletRequest request, HttpServletResponse response) - RETURNED: \n\n\t{\n\t\t auth:" +  auth + " \n}\n\n");
		log.info("attemptAuthentication(HttpServletRequest request, HttpServletResponse response) - END");
		return auth;
	}


	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		log.info("successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,\r\n" + 
				"			Authentication authResult) - START");
		
		// static method of class AuAuthenticationService. This method Return void and addToken to a response that send to front end.
		AuthenticationService.addToken(response, auth.getName()); 
		
		log.info("successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,\r\n" + 
				"			Authentication authResult) - END");
	}

}
