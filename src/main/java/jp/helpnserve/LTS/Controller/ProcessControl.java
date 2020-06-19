package jp.helpnserve.LTS.Controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.helpnserve.LTS.Model.Sentence;
import jp.helpnserve.LTS.Model.Sound;
import jp.helpnserve.LTS.Model.User;
import jp.helpnserve.LTS.Model.UserInfo;
import jp.helpnserve.LTS.Repository.DictionaryRepository;
import jp.helpnserve.LTS.Repository.SentenceRepository;
import jp.helpnserve.LTS.Repository.SoundRepository;
import jp.helpnserve.LTS.Repository.UserInfoRepository;
import jp.helpnserve.LTS.Repository.UserRepository;
import jp.helpnserve.LTS.UploadingFile.FileUploadController;

@Controller
public class ProcessControl {
	@Autowired
	private SentenceRepository sentenceRepo;
	@Autowired
	private DictionaryRepository dictionaryRepo;
	@Autowired
	private SoundRepository soundRepository;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private UserRepository userRepository;

	@PersistenceUnit
	private EntityManagerFactory emf;

	@Autowired
	private FileUploadController fileUploadController;

	@RequestMapping(value = "/content-reg", method = RequestMethod.POST)
	public String addNewSentence(@RequestParam("file") Optional<MultipartFile> file,
			@ModelAttribute("sentence") Sentence sentence, RedirectAttributes redirectAttribute) {

		// Set dictionary
		sentence.setDictionary(dictionaryRepo.findById(1).get());

		try {

			// Set file
			if (!file.isEmpty()) {

				int maxId = soundRepository.getMaxId() + 1;

				// save file on server
				fileUploadController.handleFileUpload(file.get(), Integer.toString(maxId));

				Sound sound = new Sound();
				sound.setId(maxId);
				sound.setSoundURL("/uploadDir/" + Integer.toString(maxId) + "/" + file.get().getOriginalFilename());
				sound = soundRepository.save(sound);

				// set sound file
				sentence.setSound(sound);

			}

			// save sentence
			sentenceRepo.save(sentence);
			redirectAttribute.addFlashAttribute("success", "追加は成功しました。");
		} catch (Exception ex) {
			redirectAttribute.addFlashAttribute("error", "追加は失敗しました。");
		}

		return "redirect:/contents-reg";
	}

	@RequestMapping(value = "/content-update", method = RequestMethod.POST)
	public String updateSentence(@RequestParam("file") Optional<MultipartFile> file,
			@ModelAttribute("sentence") Sentence sentence, RedirectAttributes redirectAttribute) {

		// Set file
		if (!file.isEmpty() && !file.get().isEmpty()) {

			// get old dir
			Sentence oldSentence = sentenceRepo.findById(sentence.getId()).get();

			int maxId = soundRepository.getMaxId() + 1;

			// save file on server
			fileUploadController.handleFileUpload(file.get(), Integer.toString(maxId));

			Sound sound = new Sound();
			sound.setId(maxId);
			sound.setSoundURL("/uploadDir/" + Integer.toString(maxId) + "/" + file.get().getOriginalFilename());
			sound = soundRepository.save(sound);

			// set sound file
			sentence.setSound(sound);

			// delete old sound
			// delete old directory
			if (oldSentence.getSound() != null) {
				fileUploadController.handleDeleteDirectory(oldSentence.getSound());
				soundRepository.delete(oldSentence.getSound());
			}
		} else {
			sentence.setSound(sentenceRepo.findById(sentence.getId()).get().getSound());
		}

		int result = sentenceRepo.updateSentence(sentence.getOriginSentence(), sentence.getTranslateSentence(),
				sentence.getExplanation(), sentence.getSound(), sentence.getId());

		if (result == 0) {
			redirectAttribute.addFlashAttribute("error", "変更は失敗しました。");
		} else {
			redirectAttribute.addFlashAttribute("success", "変更は成功しました。");
		}

		return "redirect:/contents-edit/" + sentence.getId();
	}

	@RequestMapping(value = "/content-delete", method = RequestMethod.POST)
	public String deleteSentence(@ModelAttribute("sentence") Sentence sentence, RedirectAttributes redirectAttribute) {

		try {
			Sentence curSentence = sentenceRepo.findById(sentence.getId()).get();

			// delete old directory
			if (curSentence.getSound() != null) {
				fileUploadController.handleDeleteDirectory(curSentence.getSound());
			}

			sentenceRepo.delete(curSentence);
			return "redirect:/contents-list";
		} catch (Exception ex) {
			redirectAttribute.addFlashAttribute("error", "削除は失敗しました。");
			return "redirect:/contents-delete/" + sentence.getId();
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/contents-search")
	public String contents_search(@RequestParam("txtId") String txtID, @RequestParam("txtEng") String txtEng,
			@RequestParam("txtJpn") String txtJap, Model model) {

		EntityManager em = emf.createEntityManager();
		StringBuilder sbSql = new StringBuilder();

		if (!txtID.isBlank()) {
			sbSql.append("u.id = " + txtID);
		}

		if (!txtEng.isBlank()) {
			if (!sbSql.toString().isBlank()) {
				sbSql.append(", ");
			}

			sbSql.append("u.originSentence like '%" + txtEng + "%'");
		}

		if (!txtJap.isBlank()) {
			if (!sbSql.toString().isBlank()) {
				sbSql.append(", ");
			}

			sbSql.append("u.translateSentence like '%" + txtJap + "%'");
		}

		List<Sentence> listSentence = null;

		if (!sbSql.toString().isBlank()) {
			listSentence = em.createQuery("FROM Sentence u WHERE " + sbSql.toString()).getResultList();
		} else {
			listSentence = sentenceRepo.findAll();
		}

		model.addAttribute("sentence", listSentence);

		User user = userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
		Optional<UserInfo> userInfo = userInfoRepository.getByUserId(user.getId());
		model.addAttribute("prevId", userInfo.get().getPrevId());

		return "contents_search";
	}

	@GetMapping(value = "/contents-search-all")
	public String contents_search(Model model) {

		model.addAttribute("sentence", sentenceRepo.findAll());

		User user = userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
		Optional<UserInfo> userInfo = userInfoRepository.getByUserId(user.getId());
		model.addAttribute("prevId", userInfo.get().getPrevId());

		return "contents_search";
	}

	@RequestMapping(value = "/contents", method = RequestMethod.GET)
	public String handleGetPrevContent() {
		User user = userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());

		Optional<UserInfo> userInfo = userInfoRepository.getByUserId(user.getId());

		if (userInfo.isEmpty()) {
			UserInfo newUserInfo = new UserInfo();

			newUserInfo.setUserId(user.getId());
			newUserInfo.setPrevId(sentenceRepo.getNextId(0).get());
			userInfoRepository.save(newUserInfo);

			return "redirect:/contents/" + newUserInfo.getPrevId();
		}

		// else
		return "redirect:/contents/" + userInfo.get().getPrevId();
	}

	@RequestMapping(value = "/contents/{id}", method = RequestMethod.GET)
	public String handleContentById(@PathVariable(name = "id") String id, Model model) {
		User user = userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
		Optional<UserInfo> userInfo = userInfoRepository.getByUserId(user.getId());
		if (userInfo.isEmpty()) {
			UserInfo newUserInfo = new UserInfo();

			newUserInfo.setUserId(user.getId());
			newUserInfo.setPrevId(sentenceRepo.getNextId(0).get());
			userInfoRepository.save(newUserInfo);
		} else {
			userInfo.get().setPrevId(Integer.parseInt(id));
			userInfoRepository.save(userInfo.get());
		}

		model.addAttribute("sentence", sentenceRepo.findById(Integer.parseInt(id)).get());

		Optional<Integer> tempId = sentenceRepo.getNextId(Integer.parseInt(id));
		model.addAttribute("nextId", tempId.isPresent() ? tempId.get() : null);

		tempId = sentenceRepo.getPrevId(Integer.parseInt(id));
		model.addAttribute("prevId", tempId.isPresent() ? tempId.get() : null);
		return "contents_item";
	}
}