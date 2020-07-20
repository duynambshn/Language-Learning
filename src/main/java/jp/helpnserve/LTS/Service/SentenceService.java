package jp.helpnserve.LTS.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.helpnserve.LTS.Model.Sentence;
import jp.helpnserve.LTS.Model.ViewSentenceInfo;
import jp.helpnserve.LTS.Repository.SentenceRepository;
import jp.helpnserve.LTS.Repository.ViewSentenceInfoRepository;

@Service
public class SentenceService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	SentenceRepository sentenceRepository;

	@Autowired
	ViewSentenceInfoRepository viewRepository;

	@PersistenceContext
	private EntityManager em;

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<ViewSentenceInfo> getAllListToShow(int userId) {

		String sqlString = "SELECT " + //
				"sen.id as id, " + //
				"sen.origin_sentence as originSentence, " + //
				"sen.translate_sentence as translateSentence, " + //
				"sen.explanation as explanation, " + //
				"sound.sound_url as soundUrl, " + //
				"info.user_id as userId, " + //
				"info.status as status, " + //
				"info.last_time as lastTime " + //
				"FROM sentence sen " + //
				"LEFT OUTER JOIN sound sound ON sen.sound_id = sound.id " + //
				"LEFT OUTER JOIN user_info info ON sen.id = info.sentence_id AND info.user_id = :userId";

		Query query = em.createNativeQuery(sqlString, ViewSentenceInfo.class);
		query.setParameter("userId", userId);

		@SuppressWarnings("unchecked")
		List<ViewSentenceInfo> result = query.getResultList();

		return result;
	}

	/**
	 * 
	 * @param userId
	 * @param sentenceId
	 * @param status
	 * @return
	 */
	public int getNextIdWithStatusDiff(int userId, int sentenceId, int status) {
		return sentenceRepository.getNextSentenceIdWithStatusDiff(userId, sentenceId, status);
	}

	/**
	 * 
	 * @param prevId
	 * @return
	 */
	public int getNextId(int prevId) {
		Optional<Sentence> nextSen = sentenceRepository.getNextSentence(prevId);

		if (nextSen.isEmpty()) {
			return 0;
		}

		return nextSen.get().getId();
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public int getNextIdWithUserId(int userId) {
		Optional<Integer> nextSen = sentenceRepository.getNextIdWithUserId(userId);

		if (nextSen.isEmpty()) {
			return 0;
		}

		return nextSen.get();
	}

	/**
	 * 
	 * @return
	 */
	public Optional<Sentence> getFirstSentence() {
		return sentenceRepository.getFirstSentence();
	}

	/**
	 * 
	 * @return
	 */
	public Optional<Sentence> getLastSentence() {
		return sentenceRepository.getLastSentence();
	}

	/**
	 * 
	 * @param sentenceId
	 * @return
	 */
	public Optional<Sentence> getSentence(int sentenceId) {

		return sentenceRepository.findById(sentenceId);
	}
}
