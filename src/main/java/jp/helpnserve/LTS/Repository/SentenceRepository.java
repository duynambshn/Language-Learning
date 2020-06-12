package jp.helpnserve.LTS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.helpnserve.LTS.Model.Sentence;

public interface SentenceRepository extends JpaRepository<Sentence, Integer> {

	@Modifying(clearAutomatically = true)
//	@Query("UPDATE Sentence u SET u.originSentence = :originSentence, u.translateSentence = :translateSentence, u.explanation = :explanation WHERE u.id = :id")
	@Query("UPDATE Sentence u SET u.originSentence = :originSentence, u.translateSentence = :translateSentence, u.explanation = :explanation where u.id = :id")
	public int updateSentence(@Param("originSentence") String originSentence,
			@Param("translateSentence") String translateSentence, @Param("explanation") String explanation,
			@Param("id") int id);

}
