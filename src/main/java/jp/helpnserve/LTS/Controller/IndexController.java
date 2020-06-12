package jp.helpnserve.LTS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.helpnserve.LTS.Model.Sentence;
import jp.helpnserve.LTS.Repository.SentenceRepository;

@Controller
public class IndexController {
	@Autowired
	private SentenceRepository sentenceRepository;

	@GetMapping(value = "/")
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("index"); // 表示メッセージ
		return mav;
	}

	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}

	@GetMapping(value = "/dictionary")
	public String dictionary() {
		return "dictionary";
	}

	@GetMapping(value = "/contents_reg")
	public String contents_reg(Model model) {

		model.addAttribute("sentence", new Sentence());

		return "contents_reg";
	}

	@GetMapping(value = "/contents_list")
	public ModelAndView contents_list(@ModelAttribute Sentence sentence, ModelAndView mav) {
		mav.setViewName("contents_list");
		mav.addObject("sentence", sentenceRepository.findAll());
		System.out.println("Sentence: " + sentence.getSentence());
		return mav;
	}

	@GetMapping(value = "/contents_edit")
	public String contentsEditForm(Model model) {
		model.addAttribute("sentence", new Sentence());
		return "contents_edit";
	}

	@PostMapping(path = "/contents_edit") // Map ONLY POST Requests
	public ModelAndView contentEditSubmit(@ModelAttribute Sentence sentence, ModelAndView mav) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		System.out.println("Sentence: " + sentence);

		sentenceRepository.save(sentence);

		// mav.setViewName("index");
		mav.addObject("sentence", new Sentence());

		return mav;
	}

	@GetMapping(value = "/return")
	public String hello() {
		return "return";
	}

	@PostMapping(value = "/return")
	public ModelAndView send(@RequestParam("name") String name, ModelAndView mav) {
		mav.setViewName("return");
		mav.addObject("msg", "Hi" + name + " !"); // 表示メッセージ
		mav.addObject("value", name); // 入力テキストに入力値を表示
		return mav;
	}
}