package com.webapp.cardatabase;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.webapp.cardatabase.service.UserDetailServiceImpl;

//	  ***************************************************
//	  *  COMMENT THIS IMPORT IF DISABLE IN MEMORY USER  *
//	  ***************************************************
//
// import com.webapp.cardatabase.service.UserDetailServiceImpl;

/**
 * @author Roberto
 * 
 *         Default behaviour of Spring security is :
 *
 *         -> THE CREATION OF USER IN MEMORY WITH: -> username: user ->
 *         password: is logged in console when application is started
 * 
 *         This class serve for configure (overrides) the default behaviour of
 *         Spring security.
 * 
 *         --------------------------------------------------------------------------------------------
 * 
 *         NOTE_1: This class must be located in root package, so in the same
 *         level of Application class that is a class that contains main method
 * 
 *         NOTE_2: The annotations:
 * 
 *         -> @Configuration -> @EnableWebSecurity
 * 
 *         allow us to switch of the default behavior of spring security
 * 
 *         NOTE_2: In this class we must ovverdide a method that have the follow
 *         signature:
 * 
 *         -> protected void configure(HttpSecurity http) throws Exception
 * 
 *         This override allow us to define a new custom configuration for
 *         spring security. Instead we define the specific endpoint that we
 *         render secure. If we want render secure all endpoint of our
 *         application, then in the override method we don't write anything
 * 
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static Logger log = LoggerFactory.getLogger(SecurityConfig.class);
	

	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
	
//	  ***************************************************
// 	  *  COMMENT THIS FIELD IF DISABLE IN MEMORY USER   *
// 	  ***************************************************
//
//	private final String USERNAME_NEW_USER = "user";
//	private final String PASSWORD_NEW_USER = "123456";
//	private final String ROLE_NEW_USER = "role";


	/**
	 * 
	 * In this method we define a custom configuration for spring security
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("configure(HttpSecurity http) - START");
		log.info("configure(HttpSecurity http) - DEBUG: \n\n\titem: {\n\t\t  http: " + http.toString() + "\n\t\t}\n");

		LoginFilter loginFilter = new LoginFilter("/login", authenticationManager());
		log.info("configure(HttpSecurity http) - DEBUG: \n\n\titem: {\n\t\t  loginFilter: " + loginFilter.toString() + "\n\t\t}\n");

		HttpSecurity httpSecurity =  http.csrf().disable().cors().and().authorizeRequests()
								             .antMatchers(HttpMethod.POST, "/login").permitAll()
																				.anyRequest().authenticated()
																											.and()
																												.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class)
																															// Filter for other requests to check JWT in header
																															.addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		log.info("configure(HttpSecurity http) - DEBUG: \n\n\titem: {\n\t\t  httpSecurity: " + httpSecurity.toString() + "\n\t\t}\n");
		log.info("configure(HttpSecurity http) - END");
	}

	
	/**
	 * This is needed for the frontend, that is sending requests from the other origin.
	 * The CORS filter intercepts requests, and if these are identified as cross origin, it adds proper headers to the
	 * request. For that, we will use Spring Security's CorsConfigurationSource interface. In this example,
	 * we will allow all HTTP methods and headers. You can define the list of allowed origins, methods, and headers here
	 * 
	 * */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		log.info("corsConfigurationSource() - START");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		log.info("configure(HttpSecurity http) - DEBUG: \n\n\tcreate item: {\n\t\t UrlBasedCorsConfigurationSource source: " + source.getCorsConfigurations() + "\n\t\t}\n");

		CorsConfiguration config = new CorsConfiguration();
		log.info("configure(HttpSecurity http) - DEBUG: \n\n\tcreate item: {\n\t\t CorsConfiguration config: " + source.getCorsConfigurations() + "\n\t\t}\n");

		config.setAllowedOrigins(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(true);
		config.applyPermitDefaultValues();
		log.info("configure(HttpSecurity http) - DEBUG: \n\n\titem: {\n\t\t CorsConfiguration config: " + source.getCorsConfigurations() + "\n\t\t}\n");

		source.registerCorsConfiguration("/**", config);
		log.info("corsConfigurationSource() - RETURNED: \n\n\titem: {\n\t\tUrlBasedCorsConfigurationSource source: " + source.getCorsConfigurations() +  "\n\t\t}\n");
		log.info("corsConfigurationSource() - END");
		
		return source;
	}
	/**
	 * We also can add other in-memory users to our application by adding the
	 * userDetailsService() method into this class
	 * 
	 * This is executed by Spring beacues is annoted with @Bean annotation. This
	 * method will create an in-memory user with: -> username user -> password :
	 * 123456
	 * 
	 * NOTE_1: this method is inherit by superclass and we override it
	 * 
	 * NOTE_2: this method contains the @Beans annotation. With @Bean annotation,
	 * when Spring is started, it serach the Component marked with @Bean and excude
	 * it.
	 */
// 		***************************************************
// 		*  COMMENT THIS CLASS IF DISABLE IN MEMORY USER   *
// 		***************************************************
//
//	@Bean
//	@Override
//	public UserDetailsService userDetailsService() {
//		log.info("userDetailsService() - START");
//		UserDetails user = org.springframework.security.core.userdetails.User
//													.withDefaultPasswordEncoder()
//														.username(USERNAME_NEW_USER).password(PASSWORD_NEW_USER).roles(ROLE_NEW_USER)
//															.build();
//	
//		log.info("userDetailsService() - DEBUG: \n\n\tuser: {\n\t\t  " + user.toString() + "\n\t\t}\n");
//		
//		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user);
//		log.info("userDetailsService() - RETURNED: \n\n\titem: {\n\t\t  " + inMemoryUserDetailsManager.toString() + "\n\t\t}\n");
//		log.info("userDetailsService() - END");
//		return inMemoryUserDetailsManager;
//	}

	/**
	 * This method is need to implement if want enable user auth from my user db and not IN MEMORY USER 
	 * 
	 * NOTE_1: @Autowired annotation is used for inject object if type AuthenticationManagerBuilder as param in this method
	 * */
//	  ***************************************************
// 	  *  COMMENT THIS METHOD IF DISABLE IN MEMORY USER   *
// 	  ***************************************************
//
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) {
//		log.info("configureGlobal(AuthenticationManagerBuilder auth) - START");
//		log.info("configureGlobal(AuthenticationManagerBuilder auth) - DEBUG: \n\n\tparam: {\n\t\t  auth: " + auth.toString() + "\n\t\t}\n");
//		
//		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//		log.info("configureGlobal(AuthenticationManagerBuilder auth) - DEBUG: \n\n\titem: {\n\t\t  BcryptPasswordEncoder: " + bCryptPasswordEncoder + "\n\t\t}\n");
//
//		try {
//			auth.userDetailsService(userDetailServiceImpl)
//						.passwordEncoder(bCryptPasswordEncoder);
//		} catch (Exception e) {
//			log.info("configureGlobal(AuthenticationManagerBuilder auth) - ERROR BCryptPasswordEncoder", e);
//			e.printStackTrace();
//		}
//		
//		log.info("configureGlobal(AuthenticationManagerBuilder auth) - END");
//	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
