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
import jp.helpnserve.LTS.Service.GeneralService;

@Controller
public class IndexController {
	@Autowired
	private SentenceRepository sentenceRepository;
	@Autowired
	GeneralService generalService;

	@GetMapping(value = "/login")
	public String login() {

		return generalService.HasLoginUser() ? "redirect:/home" : "login";
	}

	@GetMapping(value = "/mod/contents-reg")
	public String contents_reg(Model model) {

		model.addAttribute("sentence", new Sentence());

		return "mod/contents_reg";
	}

	@GetMapping(value = "/mod/contents-list")
	public ModelAndView contents_list(ModelAndView mav) {
		mav.setViewName("/mod/contents_list");
		mav.addObject("sentence", sentenceRepository.findAll());
		return mav;
	}

	@RequestMapping(value = "/mod/contents-edit/{id}", method = RequestMethod.GET)
	public String contentsEditForm(@PathVariable(name = "id") String id, Model model) {

		Sentence sentence = sentenceRepository.findById(Integer.parseInt(id)).get();

		if (sentence.getSound() != null) {
			sentence.getSound().setSoundURL(sentence.getSound().getSoundURL().equals("") ? ""
					: sentence.getSound().getSoundURL().split("/")[3]);
		}

		model.addAttribute("sentence", sentence);
		return "/mod/contents_edit";
	}

	@RequestMapping(value = "/mod/contents-delete/{id}", method = RequestMethod.GET)
	public String handleContentDelete(@PathVariable(name = "id") String id, Model model) {
		model.addAttribute("sentence", sentenceRepository.findById(Integer.parseInt(id)).get());
		return "mod/contents_delete";
	}

	@RequestMapping(value = "/mod/contents-csv", method = RequestMethod.GET)
	public String handleContentCSV(String id, Model model) {
		return "mod/contents_csv";
	}

}