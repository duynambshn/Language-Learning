package jp.helpnserve.LTS.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import jp.helpnserve.LTS.Model.UserInfo;

@Transactional
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

	public Optional<UserInfo> findFirstByUserIdAndSentenceId(int userId, int sentenceId);

	public Optional<UserInfo> findFirstByUserIdAndStatus(int userId, int status);

	public List<UserInfo> findByUserId(int userId);

	public List<UserInfo> findByUserIdAndSentenceIdGreaterThanOrderBySentenceIdAsc(int userId, int sentenceId);

	public Optional<UserInfo> findFirstByUserIdAndStatusOrderBySentenceIdAsc(int userId, int status);

	public Optional<UserInfo> findFirstByUserIdAndStatusNotOrderBySentenceIdAsc(int userId, int status);

	public Optional<UserInfo> findFirstByUserIdAndStatusOrderBySentenceIdDesc(int userId, int status);

	public Optional<UserInfo> findFirstByUserIdAndSentenceIdGreaterThanAndStatusNotOrderBySentenceIdAsc(int userId,
			int sentenceId, int status);

	public Optional<UserInfo> findFirstByUserIdAndSentenceIdGreaterThanAndStatusOrderBySentenceIdAsc(int userId,
			int sentenceId, int status);

	@Query("FROM UserInfo u WHERE u.userId = :id")
	public Optional<UserInfo> getByUserId(@Param("id") int userId);

	@Query("SELECT MAX(u) FROM UserInfo u WHERE u.userId = :id")
	public Optional<UserInfo> getMaxByUserId(@Param("id") int userId);

	@Query("FROM UserInfo u JOIN FETCH u.sentence WHERE u.userId = :id")
	public List<UserInfo> getListUserInfoById(@Param("id") int userId);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE user_info SET status = 3 WHERE user_id = :user_id AND status = 2", nativeQuery = true)
	public int updateUserInfoWhenSelect(@Param("user_id") int user_id);

	@Modifying(clearAutomatically = true)
	@Query(value = "TRUNCATE TABLE user_info", nativeQuery = true)
	public int truncateUserInfo();

}
