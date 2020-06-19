package jp.helpnserve.LTS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.helpnserve.LTS.Model.Sentence;
import jp.helpnserve.LTS.Repository.SentenceRepository;

@Controller
public class IndexController {
	@Autowired
	private SentenceRepository sentenceRepository;

	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}

	@GetMapping(value = "/contents-reg")
	public String contents_reg(Model model) {

		model.addAttribute("sentence", new Sentence());

		return "contents_reg";
	}

	@GetMapping(value = "/contents-list")
	public ModelAndView contents_list(ModelAndView mav) {
		mav.setViewName("contents_list");
		mav.addObject("sentence", sentenceRepository.findAll());
		return mav;
	}

	@RequestMapping(value = "/contents-edit/{id}", method = RequestMethod.GET)
	public String contentsEditForm(@PathVariable(name = "id") String id, Model model) {
		model.addAttribute("sentence", sentenceRepository.findById(Integer.parseInt(id)).get());
		return "contents_edit";
	}

	@RequestMapping(value = "/contents-delete/{id}", method = RequestMethod.GET)
	public String handleContentDelete(@PathVariable(name = "id") String id, Model model) {
		model.addAttribute("sentence", sentenceRepository.findById(Integer.parseInt(id)).get());
		return "contents_delete";
	}

//	@GetMapping(value = "/contents-play")
//	public String contents_play(Model model) {
//
//		model.addAttribute("sentence", sentenceRepository.findAll());
//
//		return "contents_play";
//	}
}