//    ***************************************************************************************************
//	  *   COMMENT THIS CLASS IF I WANT USE JWT FOR AUTHENTICATION AND AUTHORIZATION INSTEAD BASIC_AUTH  *
//	  ***************************************************************************************************
//package com.webapp.cardatabase.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.webapp.cardatabase.domain.User;
//import com.webapp.cardatabase.domain.UserRepository;
//
//
///**
// * @author Roberto
// * 
// * Spring security use an interface called UserDetailService for 
// * 	
// * 		-> user authentication
// * 		-> user authorization
// * 
// * NOTE: this class must be located in a root package called service, 
// * 
// * */
//@Service
//public class UserDetailServiceImpl implements UserDetailsService {
//
//	private static Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);
//	
//	@Autowired
//	private UserRepository userRepo;
//	
//	/**
//	 * 
//	 * This method is inherit from superclass (the interface UserDetailsService) and ovveride it in this class
//	 * 
//	 * */
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		log.info("loadUserByUsername(String username) - START");
//		log.info("loadUserByUsername(String username) - DEBUG: \n\n\tparam: {\n\t\t  username: " + username + "\n\t\t}\n");
//		
//		// fetched a user form db specifying password of user i want fetch
//		User userFetchedDB = userRepo.findByUsername(username);
//		log.info("loadUserByUsername(String username) - DEBUG: \n\n\titem: {\n\t\t  userFetchDB: " + userFetchedDB.toString() + "\n\t\t}\n");
//		
//		// create a new User (class of Spring security for manage user authentication and autorization
//		// and save it reference in a variable of type UserDetails (subclass or interface of User)
//		UserDetails newUserDetails = new org.springframework.security.core.userdetails.User( userFetchedDB.getUsername(),
//																					  userFetchedDB.getPassword(), 
//																					  true,
//																					  true,
//																					  true,
//																					  true,
//																					  AuthorityUtils.createAuthorityList(userFetchedDB.getRole()));
//		
//		log.info("loadUserByUsername(String username) - DEBUG: \n\n\titem: {\n\t\t  newUserDetails: " + newUserDetails.toString() + "\n\t\t}\n");
//		log.info("loadUserByUsername(String username) - END");
//		return newUserDetails;
//	}
//
//}
