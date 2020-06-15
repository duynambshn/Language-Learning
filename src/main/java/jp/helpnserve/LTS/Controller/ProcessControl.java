package jp.helpnserve.LTS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.helpnserve.LTS.Model.Sentence;
import jp.helpnserve.LTS.Repository.DictionaryRepository;
import jp.helpnserve.LTS.Repository.SentenceRepository;

@Controller
public class ProcessControl {
	@Autowired
	private SentenceRepository sentenceRepo;
	@Autowired
	private DictionaryRepository dictionaryRepo;

	@RequestMapping(value = "/content-reg", method = RequestMethod.POST)
	public String addNewSentence(@ModelAttribute("sentence") Sentence sentence, RedirectAttributes redirectAttribute) {

		sentence.setDictionary(dictionaryRepo.findById(1).get());
		try {
			sentenceRepo.save(sentence);
			redirectAttribute.addFlashAttribute("success", "追加は成功しました。");
		} catch (Exception ex) {
			redirectAttribute.addFlashAttribute("error", "追加は失敗しました。");
		}

		return "redirect:/contents-reg";
	}

	@RequestMapping(value = "/content-update", method = RequestMethod.POST)
	public String updateSentence(@ModelAttribute("sentence") Sentence sentence, RedirectAttributes redirectAttribute) {

		int result = sentenceRepo.updateSentence(sentence.getOriginSentence(), sentence.getTranslateSentence(),
				sentence.getExplanation(), sentence.getId());

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