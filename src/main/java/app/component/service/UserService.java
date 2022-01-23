package app.component.service;

import java.util.List;
import java.util.Optional;

import app.component.User;

public interface UserService {
	List<User> findAll();
	Optional<User> findById(String id);
	User save(User user);
	void update(User user);
	void delete(User user);
	List<User> findByHandle(String handle);
}// - UserService
