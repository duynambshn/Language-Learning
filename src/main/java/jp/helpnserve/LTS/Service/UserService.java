package jp.helpnserve.LTS.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.helpnserve.LTS.Model.User;
import jp.helpnserve.LTS.Repository.AuthorizedRepository;
import jp.helpnserve.LTS.Repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthorizedRepository authReposity;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * 
	 * @param password
	 * @param rePassword
	 * @return
	 */
	public boolean isPasswordValid(String password, String rePassword) {
		return password.equals(rePassword) ? true : false;
	}

	/**
	 * 
	 * @return
	 */
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Optional<User> getUserById(int id) {
		return userRepository.findByIdAndGetAuthorized(id);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public User getUserByUserName() {
		return userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
	}
}
