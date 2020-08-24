package jp.helpnserve.LTS.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.helpnserve.LTS.Model.Authorized;
import jp.helpnserve.LTS.Model.Dictionary;
import jp.helpnserve.LTS.Model.Sentence;
import jp.helpnserve.LTS.Model.Sound;
import jp.helpnserve.LTS.Model.User;
import jp.helpnserve.LTS.Model.UserInfo;
import jp.helpnserve.LTS.Model.ViewSentenceInfo;
import jp.helpnserve.LTS.Repository.AuthorizedRepository;
import jp.helpnserve.LTS.Repository.DictionaryRepository;
import jp.helpnserve.LTS.Repository.SentenceRepository;
import jp.helpnserve.LTS.Repository.SoundRepository;
import jp.helpnserve.LTS.Repository.UserInfoRepository;
import jp.helpnserve.LTS.Repository.UserRepository;
import jp.helpnserve.LTS.Service.CSVHelper;
import jp.helpnserve.LTS.Service.SentenceService;
import jp.helpnserve.LTS.Service.SoundService;
import jp.helpnserve.LTS.Service.UserInfoService;
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
	@Autowired
	private AuthorizedRepository authRepository;
	@Autowired
	private CSVHelper csvHelperService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private SentenceService sentenceService;
	@Autowired
	private SoundService soundService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PersistenceUnit
	private EntityManagerFactory emf;

	@Autowired
	private FileUploadController fileUploadController;

	@Value("${server.servlet.context-path}")
	private String contextPath;

	/**
	 * 
	 * @param user
	 * @param mav
	 * @return
	 */
	@PostMapping(path = "/admin/signup") // Map ONLY POST Requests
	public ModelAndView signUpSubmit(@ModelAttribute User user,
			@RequestParam(name = "chk_mod", required = false) Boolean chk_mod, ModelAndView mav) {
		// check exist user
		User exUser = userRepository.findByName(user.getName());

		if (exUser != null) {
			mav.setViewName("/admin/signup");
			mav.addObject("error", "username : '" + user.getName() + "' has exists");
			return mav;
		}

		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		System.out.println("User: " + user);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		// set user auth
		Set<Authorized> listAuthorized = new HashSet<Authorized>();
		listAuthorized.add(authRepository.findByName("ROLE_USER"));
		if (chk_mod != null && chk_mod) {
			listAuthorized.add(authRepository.findByName("ROLE_MOD"));
		}

		user.setListAuthorized(listAuthorized);
		userRepository.save(user);

		mav.setViewName("/admin/signup");
		mav.addObject("success", "username : '" + user.getName() + "' has created");

		return mav;
	}

	// ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝MODERATOR PATH＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝ //
	/**
	 * 
	 * @param file
	 * @param sentence
	 * @param redirectAttribute
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/mod/content-reg", method = RequestMethod.POST)
	public String addNewSentence(@RequestParam("file") Optional<MultipartFile> file,
			@ModelAttribute("sentence") Sentence sentence, RedirectAttributes redirectAttribute) {

		// Set dictionary
		sentence.setDictionary(dictionaryRepo.findById(1).get());

		try {

			// Set file
			if (!file.isEmpty() && !file.get().isEmpty()) {

				int maxId = soundRepository.getMaxId();

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

		return "redirect:/mod/contents-reg";
	}

	/**
	 * 
	 * @param file
	 * @param sentence
	 * @param redirectAttribute
	 * @return
	 */
	@RequestMapping(value = "/mod/content-update", method = RequestMethod.POST)
	public String updateSentence(@RequestParam("file") Optional<MultipartFile> file,
			@ModelAttribute("sentence") Sentence sentence, RedirectAttributes redirectAttribute) {

		// Set file
		if (!file.isEmpty() && !file.get().isEmpty()) {

			// get old dir
			Sentence oldSentence = sentenceRepo.findById(sentence.getId()).get();

			int maxId = soundRepository.getMaxId();

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

		return "redirect:/mod/contents-edit/" + sentence.getId();
	}

	/**
	 * 
	 * @param sentence
	 * @param redirectAttribute
	 * @return
	 */
	@RequestMapping(value = "/mod/content-delete", method = RequestMethod.POST)
	public String deleteSentence(@ModelAttribute("sentence") Sentence sentence, RedirectAttributes redirectAttribute) {

		try {
			Sentence curSentence = sentenceRepo.findById(sentence.getId()).get();

			// delete old directory
			if (curSentence.getSound() != null) {
				fileUploadController.handleDeleteDirectory(curSentence.getSound());
			}

			sentenceRepo.delete(curSentence);
			return "redirect:/mod/contents-list";
		} catch (Exception ex) {
			redirectAttribute.addFlashAttribute("error", "削除は失敗しました。");
			return "redirect:/mod/contents-delete/" + sentence.getId();
		}
	}
	// ------------------------------------------------

	/**
	 * 
	 * @param txtID
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/mod/contents-search")
	public String contents_search(@RequestParam("txtId") String txtID, Model model) {

		EntityManager em = emf.createEntityManager();
		StringBuilder sbSql = new StringBuilder();

		if (!txtID.isBlank()) {
			sbSql.append("u.id = " + txtID);
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
		model.addAttribute("prevId", userInfo.get().getUserId());

		return "/mod/contents_search";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/mod/contents-search-all")
	public String contents_search(Model model) {

		model.addAttribute("sentence", sentenceRepo.findAll());

		User user = userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
		Optional<UserInfo> userInfo = userInfoRepository.getByUserId(user.getId());
		model.addAttribute("prevId", userInfo.get().getUserId());

		return "/mod/contents_search";
	}
	// ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝MODERATOR PATH＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝ //

	// ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝USER PATH＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝ //
	/**
	 * 
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user/contents", method = RequestMethod.GET)
	public String handleContentById(Model model) {

		User user = userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
//		Optional<UserInfo> userInfo = userInfoRepository.getMaxByUserId(user.getId());
		Optional<UserInfo> userInfo = userInfoService.getSelectingUserInfoWithUserId(user.getId(), 2);
		Sentence sentence = new Sentence();

		if (userInfo.isPresent()) {
			userInfo.get().setSentence(userInfo.get().getSentence());
			userInfoRepository.save(userInfo.get());
			sentence = sentenceRepo.findById(userInfo.get().getSentence().getId()).get();
		} else {
			UserInfo newUserInfo = new UserInfo();

			if (!userInfoService.hasUserInfoData(user.getId())) {

				newUserInfo.setUserId(user.getId());
				newUserInfo.setSentence(sentenceService.getFirstSentence().get());
				newUserInfo.setStatus(2);
				userInfoRepository.save(newUserInfo);
				sentence = sentenceRepo.findById(newUserInfo.getSentence().getId()).get();
			} else {

				int nextSentenceId = sentenceService.getNextIdWithUserId(user.getId());

				if (nextSentenceId > 0) {
					sentence = sentenceRepo.findById(nextSentenceId).get();
					newUserInfo.setUserId(user.getId());
					newUserInfo.setSentence(sentence);
					newUserInfo.setStatus(2);
					userInfoRepository.save(newUserInfo);
				} else {
					sentence = sentenceService.getLastSentence().get();
				}

			}
		}

		model.addAttribute("sentence", sentence);

		List<ViewSentenceInfo> listSentence = sentenceService.getAllListToShow(user.getId());

		model.addAttribute("listSentence", listSentence);
		return "/user/contents";
	}

	@RequestMapping(value = "/user/update-info", method = RequestMethod.POST)
	public String handleUpdateInfo(@RequestParam int id, @RequestParam boolean isGood, ModelMap map) {

		// get user by name
		User user = userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());

		// Get and Set UserInfo status
		UserInfo userInfo = userInfoRepository.findFirstByUserIdAndSentenceId(user.getId(), id).get();
		userInfo.setStatus(isGood ? 1 : 0);
		// save UserInfo
		userInfoService.updateUserInfo(userInfo);

		Optional<Sentence> sentence = sentenceRepo.getNextSentence(id);

		map.addAttribute("sentence", sentence.get());

		return "/user/contents :: player";
	}
	// ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝USER PATH＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝ //

	// ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝CSV処理＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝//
	/**
	 * 
	 * @param file
	 * @param redirectAttribute
	 * @return
	 */
	@RequestMapping(value = "/mod/csv-reg", method = RequestMethod.POST)
	public String addNewSentenceFromCSV(@RequestParam("csvInput") MultipartFile file,
			@RequestParam("audioInput") MultipartFile[] audio, RedirectAttributes redirectAttribute) {

		int row = 0;

		try {

			if (file.isEmpty() || !csvHelperService.hasCSVFormat(file)) {
				redirectAttribute.addAttribute("error", "CSVファイルを選択してください。");
				return "redirect:/mod/content-csv";
			}

			List<String[]> csvData = csvHelperService.csvToList(file.getInputStream());

			Dictionary dictionary = dictionaryRepo.findById(1).get();
			Sentence sentence;

			// DELETE AND RESET AUTOINCREMENT
			sentenceRepo.setForeignKey(0);
			sentenceRepo.truncateSentence();
			userInfoRepository.truncateUserInfo();
			soundRepository.truncateSound();
			sentenceRepo.setForeignKey(1);
			// DELETE SOUND FILE
			fileUploadController.handleDeleteAll();

			for (String[] data : csvData) {

				// processing row
				row++;

				sentence = new Sentence();
				sentence.setDictionary(dictionary);
				sentence.setOriginSentence(data[1]);
				sentence.setTranslateSentence(data[2]);
				sentence.setExplanation(data[3]);

				if (data[4] != "") {
					Optional<MultipartFile> input = Arrays.stream(audio)
							.filter(x -> x.getOriginalFilename().equals(data[4])).findFirst();

					if (input.isPresent()) {
						sentence.setSound(soundService.CreateNewSound(input.get()));
					}

					audio = ArrayUtils.removeElement(audio, input);
				}

				sentenceRepo.save(sentence);
			}

			redirectAttribute.addFlashAttribute("success", "追加は成功しました。");
		} catch (Exception e) {
			System.out.print(e);
			redirectAttribute.addFlashAttribute("error", row + "行にエラーが発生しました。");
		}

		return "redirect:/mod/contents-csv";
	}

	/**
	 * 
	 * @param file
	 * @param redirectAttribute
	 * @return
	 */
	@RequestMapping(value = "/mod/csv-download", method = RequestMethod.GET)
	public String createFileCSV(RedirectAttributes redirectAttribute, HttpServletResponse response) {
		try {
			// get your file as InputStream
			ByteArrayInputStream is = csvHelperService.backupToCSV(sentenceRepo.findAll());
			byte[] buffer = new byte[is.available()];
			is.read(buffer);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");

			File targetFile = new File("src/main/resources/backup-" + sdf.format(new Date()) + ".csv");

			OpenOption myOptions[] = { StandardOpenOption.WRITE, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING };
			Files.write(targetFile.toPath(), buffer, myOptions);

			redirectAttribute.addFlashAttribute("success", "CSVファイルを作成しました。");

		} catch (Exception e) {
			redirectAttribute.addFlashAttribute("error", "エラーが発生しました。");
		}

		return "redirect:/mod/contents-csv";
	}
	// ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝CSV処理＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝//
}