package jp.helpnserve.LTS.Repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

	@Query(value = "SELECT u.id FROM Sentence u WHERE u.id > :id ORDER BY u.id LIMIT 1", nativeQuery = true)
	public Optional<Integer> getNextId(@Param("id") int id);

	@Query(value = "SELECT u.id FROM Sentence u WHERE u.id < :id ORDER BY u.id desc LIMIT 1", nativeQuery = true)
	public Optional<Integer> getPrevId(@Param("id") int id);

}
