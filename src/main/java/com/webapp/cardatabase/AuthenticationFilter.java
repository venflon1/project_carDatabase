package com.webapp.cardatabase;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.webapp.cardatabase.service.AuthenticationService;


/**
 * This class extends GenericFilterBean, which is a generic superclass for any type of filter.
 * This class will handle authentication in all other endpoints except '/login'.
 * 
 * The AuthenticationFilter uses the addAuthentication method from our service class 
 * to get a token from the request Authorization header
 * */
public class AuthenticationFilter extends GenericFilter{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4911068842991139762L;
	
	private static Logger log = Logger.getLogger(AuthenticationFilter.class);
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		log.info("doFilter(ServletRequest request, ServletResponse response, FilterChain chain) - START");
		
		// AuthenticationServise is a class create before in package com.webapp.cardatabase.service
		// use a static method getAuthentication of class AuthenticationService
		Authentication auth = AuthenticationService.getAuthentication((HttpServletRequest)request);
		log.info("userDetailsService() - DEBUG: \n\n\tuser: {\n\t\tauth"+ auth.toString() + "\n\t\t}\n");
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		log.info("userDetailsService() - DEBUG: \n\n\tuser: {\n\t\tauth"+ auth.toString() + "\n\t\t}\n");

		filterChain.doFilter(request, response);
		log.info("doFilter(ServletRequest request, ServletResponse response, FilterChain chain) - END");
	}

	
}
