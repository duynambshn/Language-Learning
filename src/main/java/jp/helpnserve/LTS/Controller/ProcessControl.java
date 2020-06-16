package jp.helpnserve.LTS.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.helpnserve.LTS.Model.Sentence;
import jp.helpnserve.LTS.Model.Sound;
import jp.helpnserve.LTS.Repository.DictionaryRepository;
import jp.helpnserve.LTS.Repository.SentenceRepository;
import jp.helpnserve.LTS.Repository.SoundRepository;
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
		if (!file.isEmpty()) {

			// get old dir
			Sentence oldSentence = sentenceRepo.findById(sentence.getId()).get();

			int maxId = soundRepository.getMaxId() + 1;

			// save file on server
			fileUploadController.handleFileUpload(file.get(), Integer.toString(maxId));
			// delete old directory
			fileUploadController.handleDeleteDirectory(oldSentence.getSound());

			Sound sound = new Sound();
			sound.setId(maxId);
			sound.setSoundURL("/uploadDir/" + Integer.toString(maxId) + "/" + file.get().getOriginalFilename());
			sound = soundRepository.save(sound);

			// set sound file
			sentence.setSound(sound);
			// delete old sound
			soundRepository.delete(oldSentence.getSound());
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

	@RequestMapping(value = "/content-del/{id}", method = RequestMethod.POST)
	public String deleteSentence(@PathVariable("id") String id, RedirectAttributes redirectAttribute) {

		int result = sentenceRepo.deleteSentence(Integer.parseInt(id));

		if (result == 0) {
			redirectAttribute.addAttribute("error", "削除は失敗しました。");
		} else {
			redirectAttribute.addAttribute("success", "削除は成功しました。");
		}

		return "redirect:/contents-list";
	}

}