package com.webapp.cardatabase.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {

	public Optional<Car> findById(Long id);

	public List<Car> findByBrand(String brand);

	public List<Car> findByModel(String model);

	public List<Car> findByColor(String color);

	public Car findByRegisterNumber(String registerNumber);

	public List<Car> findByYear(int year);

	public List<Car> findByPrice(int price);

	public List<Car> findByBrandAndModel(String model, String brand);

	public List<Car> findByBrandAndColor(String model, String color);

	public List<Car> findByBrandAndModelAndYear(String brand, String model, int year);

	public List<Car> findByModelAndColorOrBrandAndYear(String model, String color, String brand, int year);

	public List<Car> findByBrandOrderByYearAsc(String brand);

	public List<Car> findByBrandAndModelOrderByBrandAsc(String brand, String model);

	public List<Car> findByModelOrderByYearDesc(String model);

	public List<Car> findByModelOrderByYearAsc(String model);

	// Fetch cars by brand using SQL
	@Query("select c from Car c where c.brand = ?1")
	List<Car> findByBrand2(String brand);
	
	// Fetch cars by brand using SQL
	@Query("select c from Car c where c.brand like %?1")
	List<Car> findByBrandEndsWith(String brand);
	
	

}
