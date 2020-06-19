package jp.helpnserve.LTS.Repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.helpnserve.LTS.Model.UserInfo;

@Transactional
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

	@Query("FROM UserInfo u WHERE u.userId = :id")
	public Optional<UserInfo> getByUserId(@Param("id") int userId);

}
