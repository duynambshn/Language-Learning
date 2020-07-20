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

//		Optional<UserInfo> searchInfo = null;

		// Find next user info
//		searchInfo = userInfoRepository.
//				.findFirstByUserIdAndSentenceIdGreaterThanAndStatusNotOrderBySentenceIdAsc(userId, sentenceId, 1);

//		while (true) {
//			nextSenId = sentenceService.getNextId(sentenceId);
//
//			if (nextSenId >= searchInfo.get().getSentence().getId()) {
//				checkInfo = userInfoRepository.findFirstByUserIdAndSentenceId(userId, nextSenId);
//			}
//			break;
//		}
//
//		if (searchInfo.isPresent()) {
//			if (nextSenId >= searchInfo.get().getSentence().getId()) {
//				searchInfo.get().setStatus(2);
//				searchInfo.get().setLastTime(new Timestamp(System.currentTimeMillis()));
//				userInfoRepository.save(searchInfo.get());
//				return searchInfo.get();
//			} else {
//				createUserInfo(userId, nextSenId, 2);
//				return userInfoRepository.findFirstByUserIdAndStatus(userId, 2).get();
//			}
//		}
//
//		// Get next sentence
//		nextSenId = sentenceService.getNextIdWithUserId(userId);
//		if (nextSenId > 0) {
//			createUserInfo(userId, nextSenId, 2);
//			return userInfoRepository.findFirstByUserIdAndSentenceId(userId, nextSenId).get();
//		}

		// NOTGOOD sentence
//		searchInfo = userInfoRepository.findFirstByUserIdAndStatusNotOrderBySentenceIdAsc(userId, 1);
//		if (searchInfo.isPresent()) {
//			searchInfo.get().setStatus(2);
//			searchInfo.get().setLastTime(new Timestamp(System.currentTimeMillis()));
//			userInfoRepository.save(searchInfo.get());
//			return searchInfo.get();
//		}

		// Find next user info
		int nextSenId = sentenceService.getNextIdWithStatusDiff(userId, sentenceId, 1);

		if (nextSenId > 0) {
			return getInfoByUserIdAndSentenceId(userId, nextSenId);
		} else { // max id case
			nextSenId = sentenceService.getNextIdWithStatusDiff(userId, 0, 1);
			if (nextSenId > 0) {
				return getInfoByUserIdAndSentenceId(userId, nextSenId);
			}
		}

		return null;
	}

	/**
	 * 
	 * @param userId
	 * @param sentenceId
	 * @return
	 */
	public UserInfo getInfoByUserIdAndSentenceId(int userId, int sentenceId) {
		Optional<UserInfo> searchInfo = null;
		searchInfo = userInfoRepository.findFirstByUserIdAndSentenceId(userId, sentenceId);

		if (searchInfo.isPresent()) {
			searchInfo.get().setStatus(2);
			searchInfo.get().setLastTime(new Timestamp(System.currentTimeMillis()));
			userInfoRepository.save(searchInfo.get());

			return searchInfo.get();
		} else {
			UserInfo newUserInfo = new UserInfo();

			newUserInfo.setSentence(sentenceService.getSentence(sentenceId).get());
			newUserInfo.setUserId(userId);
			newUserInfo.setStatus(2);
			newUserInfo.setLastTime(new Timestamp(System.currentTimeMillis()));
			userInfoRepository.save(newUserInfo);

			return newUserInfo;
		}
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
