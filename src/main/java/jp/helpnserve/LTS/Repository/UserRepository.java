package jp.helpnserve.LTS.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.helpnserve.LTS.Model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByName(String username);

	User findById(int id);

	@Query("FROM User u JOIN FETCH u.listAuthorized WHERE u.name = :userName")
	Optional<User> findByUserNameAndGetAuthorized(@Param("userName") String userName);

	@Query("FROM User u JOIN FETCH u.listAuthorized WHERE u.id = :id")
	Optional<User> findByIdAndGetAuthorized(@Param("id") int id);

}
