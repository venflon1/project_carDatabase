package com.webapp.cardatabase.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Owner {

	// Specifica che la proprietà o il campo non è persistente. 
	// Viene utilizzato per annotare una proprietà o un campo di una classe di entità, superclasse mappata o classe incorporabile.
	@Transient
	private Logger log = LoggerFactory.getLogger(Car.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_OWNER")
	private Long id;

	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.LAZY)
	private List<Car> cars;

	public Owner(String firstName, String lastName) {
		super();
		log.info("constructor Owner(String firstName, String lastName) - START");

		this.firstName = firstName;
		this.lastName = lastName;
		
		log.info("constructor Owner(String firstName, String lastName) - DEBUG: \nstate: " + 
				"{\n" + 
					"\t firstName: " + this.firstName + "\n" +
					"\t lastName: "  + this.lastName + "\n" + 
				"\n}");
		
		log.info("constructor Owner(String firstName, String lastName) - START");
	}
	
	public Owner() {
		this(null, null);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	@Override
	public String toString() {
		return "Owner [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
}
