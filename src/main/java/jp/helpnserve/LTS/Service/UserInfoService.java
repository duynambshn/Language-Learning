package jp.helpnserve.LTS.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.helpnserve.LTS.Model.UserInfo;
import jp.helpnserve.LTS.Repository.UserInfoRepository;

@Service
public class UserInfoService {

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	SentenceService sentenceService;
	@Autowired
	UserService userService;

	/**
	 * 
	 * @param userId
	 * @param sentenceId
	 * @return
	 */
	public UserInfo getNextUserInfo(int userId, int sentenceId) {

		Optional<UserInfo> searchInfo = null;

		// Find next user info
		searchInfo = userInfoRepository
				.findFirstByUserIdAndSentenceIdGreaterThanAndStatusNotOrderBySentenceIdAsc(userId, sentenceId, 1);
		if (searchInfo.isPresent()) {
			searchInfo.get().setStatus(2);
			searchInfo.get().setLastTime(new Timestamp(System.currentTimeMillis()));
			userInfoRepository.save(searchInfo.get());
			return searchInfo.get();
		}

		// Get next sentence
		int nextSenId = sentenceService.getNextIdWithUserId(userId);
		if (nextSenId > 0) {
			createUserInfo(userId, nextSenId, 2);
			return userInfoRepository.findFirstByUserIdAndSentenceId(userId, nextSenId).get();
		}

		// NOTGOOD sentence
		searchInfo = userInfoRepository.findFirstByUserIdAndStatusNotOrderBySentenceIdAsc(userId, 1);
		if (searchInfo.isPresent()) {
			searchInfo.get().setStatus(2);
			searchInfo.get().setLastTime(new Timestamp(System.currentTimeMillis()));
			userInfoRepository.save(searchInfo.get());
			return searchInfo.get();
		}

		return null;
	}

	/**
	 * 
	 * @param userId
	 * @param sentenceId
	 * @return
	 */
	public int getSelectUserInfo(int userId, int sentenceId) {

		// Update prev Sentence
		userInfoRepository.updateUserInfoWhenSelect(userId);

		// Update select Sentence
		UserInfo selectInfo;
		if (hasUserInfoDataWithSentenceId(userId, sentenceId)) {
			selectInfo = userInfoRepository.findFirstByUserIdAndSentenceId(userId, sentenceId).get();
		} else {
			selectInfo = new UserInfo();
			selectInfo.setUserId(userId);
			selectInfo.setSentence(sentenceService.getSentence(sentenceId).get());
		}

		selectInfo.setStatus(2);
		userInfoRepository.save(selectInfo);

		return 1;
	}

	/**
	 * 
	 * @param userId
	 * @param status
	 * @return
	 */
	public Optional<UserInfo> getSelectingUserInfoWithUserId(int userId, int status) {
		return userInfoRepository.findFirstByUserIdAndStatus(userId, status);
	}

//	public UserInfo getMaxSentenceByUserId(int userId) {
//		// Find next user info
//		UserInfo searchInfo = userInfoRepository
//				.findFirstByUserIdAndSentenceIdGreaterThanAndStatusNotOrderBySentenceIdAsc(userId);
//	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public boolean hasUserInfoData(int userId) {

		List<UserInfo> listUserInfo = userInfoRepository.findByUserId(userId);

		if (listUserInfo.isEmpty()) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public boolean hasUserInfoDataWithSentenceId(int userId, int sentenceId) {

		Optional<UserInfo> userInfo = userInfoRepository.findFirstByUserIdAndSentenceId(userId, sentenceId);

		if (userInfo.isEmpty()) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param userId
	 * @param sentenceId
	 * @param status
	 * @return
	 */
	public int updateUserInfoStatus(int userId, int sentenceId, int status) {

		UserInfo userInfo = userInfoRepository.findFirstByUserIdAndSentenceId(userId, sentenceId).get();
		userInfo.setStatus(status);
		updateUserInfo(userInfo);

		return 1;
	}

	/**
	 * 
	 * @param userInfo
	 * @return
	 */
	public int updateUserInfo(UserInfo userInfo) {
		userInfoRepository.save(userInfo);

		return 1;
	}

	/**
	 * 
	 * @param sentenceId
	 * @param userId
	 * @return
	 */
	public int createUserInfo(int userId, int sentenceId, int status) {

		UserInfo userInfo = new UserInfo();
		userInfo.setSentence(sentenceService.getSentence(sentenceId).get());
		userInfo.setUserId(userId);
		userInfo.setStatus(status);

		userInfoRepository.save(userInfo);

		return 1;
	}

	public Optional<UserInfo> getUserInfo(int userId, int sentenceId) {
		return userInfoRepository.findFirstByUserIdAndSentenceId(userId, sentenceId);
	}
}
