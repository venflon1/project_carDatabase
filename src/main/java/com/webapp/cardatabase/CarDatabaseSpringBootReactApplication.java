package com.webapp.cardatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.webapp.cardatabase.domain.Car;
import com.webapp.cardatabase.domain.CarRepository;
import com.webapp.cardatabase.domain.Owner;
import com.webapp.cardatabase.domain.OwnerRepository;
import com.webapp.cardatabase.domain.User;
import com.webapp.cardatabase.domain.UserRepository;

/**
 * @author Roberto 
 * 
 * */
@SpringBootApplication
public class CarDatabaseSpringBootReactApplication {

	private static Logger log = LoggerFactory.getLogger(CarDatabaseSpringBootReactApplication.class);

	@Autowired
	private OwnerRepository ownerRepo;
	
	@Autowired
	private CarRepository carRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CarDatabaseSpringBootReactApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner() {
		return (args) -> {
			log.info("runner() - START");
			
			// create two object Owner and save into db
			Owner own_1 = new Owner("Jhon", "Jhonson");
			Owner own_2 = new Owner("Mary", "Robinson");
			ownerRepo.save(own_1);
			ownerRepo.save(own_2);
			
			// create three object Car and save into db
			Car a = new Car("Ford", "Mustang", "Red", "ADF-1121", 2017, 39000, own_1);
			Car b = new Car("Nissan", "Leaf", "White", "SSJ-3002", 2014, 19000, own_2);
			Car c = new Car("toyota", "Prius", "Silver", "KKO-0212", 2018, 39000, own_2);
			carRepo.save(a);
			carRepo.save(b);
			carRepo.save(c);
			
			
			
			
			//create a BCryptPasswordEncode for crypt a password users
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			
			// create thwo object User and save into db
			User normalUser = new User("user", bCryptPasswordEncoder.encode("qwerty"), "USER");
			User adminUser = new User("admin", bCryptPasswordEncoder.encode("admin"), "ADMIN");
			userRepo.save(normalUser);
			userRepo.save(adminUser);
						
			log.info("runner() - END");
		};
	}
}
