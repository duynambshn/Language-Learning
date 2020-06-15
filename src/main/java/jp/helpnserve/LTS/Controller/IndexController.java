package jp.helpnserve.LTS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		model.addAttribute("sentence", sentenceRepository.findById(Integer.parseInt(id)));
		return "contents_play";
	}

	@GetMapping(value = "/contents-play")
	public String contents_play(Model model) {

		model.addAttribute("sentence", sentenceRepository.findAll());

		return "contents_play";
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