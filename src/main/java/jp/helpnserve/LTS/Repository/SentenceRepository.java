package jp.helpnserve.LTS.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import jp.helpnserve.LTS.Model.Sentence;
import jp.helpnserve.LTS.Model.Sound;

@Transactional
public interface SentenceRepository extends JpaRepository<Sentence, Integer> {

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Sentence u SET u.originSentence = :originSentence, u.translateSentence = :translateSentence, u.explanation = :explanation, u.sound = :sound where u.id = :id")
	public int updateSentence(@Param("originSentence") String originSentence,
			@Param("translateSentence") String translateSentence, @Param("explanation") String explanation,
			@Param("sound") Sound sound, @Param("id") int id);

	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM Sentence u WHERE u.id = :id")
	public int deleteSentence(@Param("id") int id);

	@Query(value = "SELECT u.id FROM sentence u WHERE u.id > :id ORDER BY u.id LIMIT 1", nativeQuery = true)
	public Optional<Integer> getNextId(@Param("id") int id);

	@Query(value = "SELECT * FROM sentence u WHERE u.id > :id ORDER BY u.id LIMIT 1", nativeQuery = true)
	public Optional<Sentence> getNextSentence(@Param("id") int id);

	@Query(value = "SELECT u.id FROM sentence u WHERE u.id < :id ORDER BY u.id desc LIMIT 1", nativeQuery = true)
	public Optional<Integer> getPrevId(@Param("id") int id);

	@Query(value = "FROM Sentence u LEFT JOIN u.listUserInfo e WITH e.userId = :userId")
	public List<Sentence> getFilterByUserId(@Param("userId") int userId);

	@Query(value = "SELECT u.* FROM sentence u ORDER BY u.id ASC LIMIT 1", nativeQuery = true)
	public Optional<Sentence> getFirstSentence();

	@Query(value = "SELECT u.* FROM sentence u ORDER BY u.id DESC LIMIT 1", nativeQuery = true)
	public Optional<Sentence> getLastSentence();

	@Query(value = "SELECT sen.id FROM sentence sen WHERE sen.id > (SELECT max(info.sentence_id) FROM user_info info WHERE info.user_id = :userId) ORDER BY sen.id LIMIT 1", nativeQuery = true)
	public Optional<Integer> getNextIdWithUserId(@Param("userId") int userId);

	@Query(value = "SELECT IFNULL((SELECT sen.id FROM sentence sen LEFT JOIN user_info info "
			+ "ON sen.id = info.sentence_id AND info.user_id = :userId "
			+ "WHERE sen.id > :sentenceId AND (info.status <> :status OR info.status is null) ORDER BY sen.id LIMIT 1), 0)", nativeQuery = true)
	public int getNextSentenceIdWithStatusDiff(@Param("userId") int userId, @Param("sentenceId") int sentenceId,
			@Param("status") int status);

	@Modifying(clearAutomatically = true)
	@Query(value = "TRUNCATE TABLE sentence", nativeQuery = true)
	public int truncateSentence();

	@Modifying(clearAutomatically = true)
	@Query(value = "SET FOREIGN_KEY_CHECKS = :foreignKey", nativeQuery = true)
	public int setForeignKey(@Param("foreignKey") int foreignKey);
}
