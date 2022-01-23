package app.component.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.component.User;


public interface UserRepository extends JpaRepository<User, String>{
	List<User> findByHandle(String handle);
	
//	@Query("SELECT id, handle FROM User")
//	List<User> findAll();

//	@Query("SELECT new app.component.User(id, handle) FROM ")
//	@Query("SELECT new app.component.User(id, handle, name, created) FROM User") // because bug ? (check Caps)
	List<User> findAll();
	
}// - UserRepository
