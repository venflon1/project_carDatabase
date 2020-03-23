package com.webapp.cardatabase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author Roberto
 * 
 * */
@Entity
public class Car {
	
	// Specifica che la proprietà o il campo non è persistente. 
	// Viene utilizzato per annotare una proprietà o un campo di una classe di entità, superclasse mappata o classe incorporabile.
	@Transient
	private Logger log = LoggerFactory.getLogger(Car.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "BRAND")
	private String brand;
	
	@Column(name = "MODEL")
	private String model;
	
	@Column(name = "COLOR")
	private String color;
	
	@Column(name = "REGISTER_NUMBER")
	private String registerNumber;
	
	@Column(name = "YEAR")
	private Integer year;
	
	@Column(name = "PRICE")
	private Integer price;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner")
	private Owner owner;

	public Car(String brand, String model, String color, String registerNumber, Integer year, Integer price, Owner owner) {
		super();
		log.info("constructor Car(String brand, String model, String color, String registerNumber, Integer year, Integer price) - START");
		
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.registerNumber = registerNumber;
		this.year = year;
		this.price = price;	
		this.owner = owner;
		log.info("constructor Car(String brand, String model, String color, String registerNumber, Integer year, Integer price) - DEBUG: \nstate: " +
				"{\n" + 
					"\t id: " +  		    this.id + "\n" +
					"\t brand: " + 		    this.brand + "\n" +
					"\t model: " +  	    this.model + "\n" +
					"\t color: " +  		this.color + "\n" +
					"\t registerNumber: " + this.registerNumber + "\n" +
					"\t year: " +  		    this.year + "\n" +
					"\t price: " +  	    this.price + "\n" +
				"}");
		
		log.info("constructor Car(Car(String brand, String model, String color, String registerNumber, Integer year, Integer price) - END");
	}


	public Car() {		
		super();
		log.info("constructor Car() - END");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRegisterNumber() {
		return registerNumber;
	}

	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Owner getOwner() {
		return owner;
	}


	public void setOwner(Owner owner) {
		this.owner = owner;
	}


	@Override
	public String toString() {
		return "Car [id=" + id + ", brand=" + brand + ", model=" + model + ", color=" + color + ", registerNumber="
				+ registerNumber + ", year=" + year + ", price=" + price + "]";
	}
}
