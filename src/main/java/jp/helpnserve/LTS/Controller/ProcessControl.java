package jp.helpnserve.LTS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public String addNewSentence(@ModelAttribute("sentence") Sentence sentence, Model model) {

		sentence.setDictionary(dictionaryRepo.findById(1).get());
		sentenceRepo.save(sentence);

		return "redirect:/contents_reg/1";
	}

	@RequestMapping(value = "/content-update", method = RequestMethod.POST)
	public String updateSentence(@ModelAttribute("sentence") Sentence sentence, Model model) {

		int result = sentenceRepo.updateSentence(sentence.getOriginSentence(), sentence.getTranslateSentence(),
				sentence.getExplanation(), sentence.getId());

		if (result == 0) {
			return "redirect:/contents-update-error/" + sentence.getId();
		} else {
			return "redirect:/contents-list";
		}
	}

	@RequestMapping(value = "/content-del/{id}", method = RequestMethod.DELETE)
	public String deleteSentence(@PathVariable("id") String id) {

		return "";
	}

}