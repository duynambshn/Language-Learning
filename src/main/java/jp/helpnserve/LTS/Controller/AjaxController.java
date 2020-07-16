package jp.helpnserve.LTS.Controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.helpnserve.LTS.Model.AjaxResponseBody;
import jp.helpnserve.LTS.Model.Sentence;
import jp.helpnserve.LTS.Model.User;
import jp.helpnserve.LTS.Model.UserInfo;
import jp.helpnserve.LTS.Repository.SentenceRepository;
import jp.helpnserve.LTS.Repository.UserInfoRepository;
import jp.helpnserve.LTS.Service.SentenceService;
import jp.helpnserve.LTS.Service.UserInfoService;
import jp.helpnserve.LTS.Service.UserService;

@RestController
@RequestMapping("/user/api/")
public class AjaxController {

	@Autowired
	UserInfoService userInfoService;
	@Autowired
	UserService userService;
	@Autowired
	SentenceRepository senRepository;
	@Autowired
	UserInfoRepository userInfoRepository;
	@Autowired
	SentenceService sentenceService;

	@RequestMapping(value = "/next-sen", method = RequestMethod.PUT)
	public ResponseEntity<?> GetSearch(@RequestBody String json) {

		AjaxResponseBody result = new AjaxResponseBody();

		JSONObject resultObject = new JSONObject(json);

		int id = Integer.parseInt(resultObject.getString("id"));
		int status = Integer.parseInt(resultObject.getString("status"));

		// Update status
		User user = userService.getUserByUserName();
		userInfoService.updateUserInfoStatus(user.getId(), id, status);

		// Get next process Sentence
		UserInfo nextInfo = userInfoService.getNextUserInfo(user.getId(), id);

		if (nextInfo == null) {
			result.setMsg("no result");
			return ResponseEntity.ok(result);
		} else {
			result.setMsg("success");
		}

		Sentence nextSentence = nextInfo.getSentence();

		result.setId(nextSentence.getId());
		result.setOriginSentence(nextSentence.getOriginSentence());
		result.setTranslateSentence(nextSentence.getTranslateSentence());
		result.setExplanation(nextSentence.getExplanation());
		result.setSoundUrl(nextSentence.getSound() != null ? nextSentence.getSound().getSoundURL() : null);
		result.setLastTime(userInfoService.getUserInfo(user.getId(), id).get().getLastTime());

		return ResponseEntity.ok(result);
	}

	@RequestMapping(value = "/select-sen", method = RequestMethod.PUT)
	public ResponseEntity<?> GetSelect(@RequestBody String json) {

		AjaxResponseBody result = new AjaxResponseBody();

		JSONObject resultObject = new JSONObject(json);

		int id = Integer.parseInt(resultObject.getString("id"));

		// Update and get select User Info
		User user = userService.getUserByUserName();
		userInfoService.getSelectUserInfo(user.getId(), id);

		Sentence sentence = sentenceService.getSentence(id).get();

		result.setMsg("success");
		result.setId(sentence.getId());
		result.setOriginSentence(sentence.getOriginSentence());
		result.setTranslateSentence(sentence.getTranslateSentence());
		result.setExplanation(sentence.getExplanation());
		result.setSoundUrl(sentence.getSound() != null ? sentence.getSound().getSoundURL() : null);
		result.setLastTime(userInfoService.getUserInfo(user.getId(), id).get().getLastTime());

		return ResponseEntity.ok(result);
	}
}