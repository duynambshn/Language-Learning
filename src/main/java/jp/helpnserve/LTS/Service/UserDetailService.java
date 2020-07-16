package jp.helpnserve.LTS.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.helpnserve.LTS.Model.User;
import jp.helpnserve.LTS.Model.UserSecurity;
import jp.helpnserve.LTS.Repository.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Optional<User> user = userRepository.findByUserNameAndGetAuthorized(userName);

		if (user.isEmpty()) {
			throw new UsernameNotFoundException(userName);
		}

		return new UserSecurity(user.get());
	}

}
