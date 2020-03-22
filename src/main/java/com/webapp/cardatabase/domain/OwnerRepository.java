package com.webapp.cardatabase.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
	
	public Optional<Owner> findById(Long id);
	
	public List<Owner> findByFirstNameAndLastName(String firstName, String lastName);

	public List<Owner> findByFirstNameAndLastNameOrderByLastName(String firstName, String lastName);

}