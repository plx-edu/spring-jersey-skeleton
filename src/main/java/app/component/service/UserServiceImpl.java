package app.component.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.component.User;
import app.component.repository.UserRepository;


@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> findAll() {
		entityManager.flush();
		entityManager.clear();
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> q = cb.createQuery(User.class);
		Root<User> u = q.from(User.class);
		
		q.multiselect(u.get("id"),
				u.get("handle"),
				u.get("name"));

		// TO DO: return getResultList() instead of variable
		List<User> result = entityManager.createQuery(q).getResultList();
		return result;
//		return userRepository.findAll();
	}

	@Override
	public Optional<User> findById(String id) {
		return userRepository.findById(id);
	}

	@Override
	public User save(User user) {
		
		// Make Salted Password Hash
//		sql = "INSERT INTO user_login (user_mail, user_handle, user_password, user_sodium) VALUES (?,?, SHA2(CONCAT(?, ?), 256), ?)";
//		try {
//			CustomMethods.generateSalt();
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return userRepository.save(user);
	}

	@Override
	public void update(User user) {
		userRepository.save(user);
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
	}

	@Override
	public List<User> findByHandle(String handle) {
		return userRepository.findByHandle(handle);
	}
}// - UserServiceImpl
