package com.webapp.cardatabase.service;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Roberto
 * 
 * This class is built for manage authentication and authorization with JWT
 * 
 * */
public class AuthenticationService {

	private static Logger log = Logger.getLogger(AuthenticationService.class);
	
	
	private final static long   EXPIRATIONTIME =  864_000_00;	// time to expire JWT
	private final static String SIGNINKEY = "SecretKey";		// the secret key string
	private final static String PREFIX = "Bearer";				// prefix schema that is tipcallìy used
		
	/**
	 * This method is used for:
	 * 		1 -> create the JWT at first call by frontend
	 * 		2 -> add to the response header the request's authorization header
	 * 
	 *  
	 *  NOTE: this method also adds Access-Control-Expose-Headers field to header with the Authorization value 
	 * */
	 public static void addToken(HttpServletResponse response, String username) {
		 log.info("addToken(HttpServletResponse response, String username) - START");
		 log.info("addToken(HttpServletResponse response, String username) - DEBUG: \n\n" + 
	 		"\n\tparameters: [" +
			 	"\n\n\t\tparam_1: {\n\t\t  response-status: "  +      response.getStatus()  + "\n\t}\n" + 
			 	"\n\t\tparam_1: {\n\t\t  response-headers-name: " + response.getHeaderNames() + "\n\t}\n" + 
			 	"\n\n\t\tparam_1: {\n\t\t  response: " + response.toString() + "\n	t}\n" +
			 	"\n\n\t\tparam_2: {\n\t\t  username: " + username + "\n	t}\n" +
			"\n\t  ]");
		 	
		 Date exipireDate = new Date(System.currentTimeMillis() + EXPIRATIONTIME);
		 
		 String JWT = Jwts.builder()
				 			.setSubject(username)
				 			.setExpiration(exipireDate)
				 				.signWith(SignatureAlgorithm.HS512, SIGNINKEY).compact();
				 																	
		 log.info("addToken(HttpServletResponse response, String username) - DEBUG: \n\n\titem: {\n\t\t jwt: " + JWT + "\n}\n");

		 String authorizationValue = PREFIX + " " + JWT;
		 String AccessControlExposeHeadersValue = "Authorization";
		 response.addHeader("Authorization", authorizationValue);
		 response.addHeader("Access-Control-Expose-Headers", AccessControlExposeHeadersValue);
		 log.info("\n\t\titem: {\n\t\t  response-headers-name: " + response.getHeaderNames() + "\n}\n");

		 
		 log.info("addToken(HttpServletResponse response, String username) - END");
	 }
	
	
	/**
	 * This method gets the JWT from request Authorization header with help of a parser method
	 * that is provided from jjwt library include into pom.xml as dependency
	 * */
	 public static Authentication getAuthentication(HttpServletRequest request) {
		 log.info("addToken(HttpServletRequest request) - START");
		 log.info("addToken(HttpServletRequest request) - DEBUG: \n\n");
		 log.info("\tparam: [" +
		 	"\n\n\t\tparam_1: {\n\t\t  request-header-Authorization: "  +  request.getHeader("Authoriaztion")  + "\n}\n" +
		 	"\n\n\t\tparam_1: {\n\t\t  request-header-Access-Control-Expose-Headers: "  +      request.getHeader("Access-Control-Expose-Headers")  + "\n}\n" +
		 "\n\t  ]");
		 
		 String token = request.getHeader("Authorization");
		 log.info("getAuthentication(HttpServletRequest request) - DEBUG: \n\n\titem: {\n\t\t jwt: " + token + "\n}\n");
	
		 String user = null;
		 if(token != null) {
			 String newToken = token.replace(PREFIX, "");
			 log.info("getAuthentication(HttpServletRequest request) - DEBUG: \n\n\titem: {\n\t\t new_jwt: " + newToken + "\n}\n");

			 user = Jwts.parser()
					 		.setSigningKey(SIGNINKEY)
					 			.parseClaimsJws(newToken)
					 				.getBody()
					 					.getSubject();
		 }
		 log.info("getAuthentication(HttpServletRequest request) - DEBUG: \n\n\titem: {\n\t\t user: " + user + "\n}\n");
		 
		 Authentication authenticationToReturnFrontEnd = null;
		 if(user != null) {
			 authenticationToReturnFrontEnd = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
		 }
		 log.info("getAuthentication(HttpServletRequest request) - DEBUG: \n\n\titem: {\n\t\t authenticationToReturnFrontEnd: " + authenticationToReturnFrontEnd + "\n}\n");
		 
		 
		 log.info("getAuthentication(HttpServletRequest request) - RETURNED \n\n\titem: {\n\t\t authenticationToReturnFrontEnd: " + authenticationToReturnFrontEnd + "\n}\n");
		 log.info("addToken(HttpServletRequest request) - END");
		 return authenticationToReturnFrontEnd;
	 }
}
