package jp.helpnserve.LTS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.helpnserve.LTS.Model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByName(String username);

}
