package jp.helpnserve.LTS.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.helpnserve.LTS.Model.ViewSentenceInfo;

@Repository
public interface ViewSentenceInfoRepository extends JpaRepository<ViewSentenceInfo, Integer> {

//	@Query(value = "SELECT  " + //
//			"sen.id as id, " + //
//			"sen.origin_sentence as originSentence, " + //
//			"sen.translate_sentence as translateSentence, " + //
//			"sen.explanation as explanation, " + //
//			"sound.soundUrl as sound_url, " + //
//			"info.status as status, " + //
//			"info.lastTime as lastTime, " + //
//			"FROM sentence sen " + //
//			"LEFT OUTER JOIN sound sound ON sen.sound_id = sound.id" + //
//			"LEFT OUTER JOIN user_info info ON sen.id = info.sentence_id and info.user_id = :userId", nativeQuery = true)
//	public List<SentenceUserInfo> getAllListSentenceUserInfo(@Param("userId") int userId);

	public List<ViewSentenceInfo> findByUserIdOrderByIdAsc(@Param("userId") int userId);
}
