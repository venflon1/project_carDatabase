package com.webapp.cardatabase.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	public Optional<User> findById(Long id);
	
	public User findByUsername(String username);
	
	public List<User> findByRole(String role);
	
	public List<User> findByUsernameAndRole(String username, String role);
	
	public List<User> findByRoleOrderByRoleAsc(String role);
}
