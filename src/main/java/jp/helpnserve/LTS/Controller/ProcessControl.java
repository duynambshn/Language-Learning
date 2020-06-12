package jp.helpnserve.LTS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.helpnserve.LTS.Model.Sentence;
import jp.helpnserve.LTS.Repository.SentenceRepository;

@Controller
public class ProcessControl {
	@Autowired
	private SentenceRepository sentenceRepo;

	@RequestMapping(value = "/content-reg", method = RequestMethod.POST)
	public String addNewDic(@ModelAttribute("sentence") Sentence sentence, Model model) {

		sentenceRepo.save(sentence);

		return "";
	}

}