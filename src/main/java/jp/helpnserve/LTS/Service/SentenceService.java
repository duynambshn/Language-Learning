package jp.helpnserve.LTS.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.helpnserve.LTS.Model.Sentence;
import jp.helpnserve.LTS.Repository.SentenceRepository;

@Service
public class SentenceService {

	@Autowired
	SentenceRepository sentenceRepository;

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
