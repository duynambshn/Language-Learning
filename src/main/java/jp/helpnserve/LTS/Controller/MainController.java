package jp.helpnserve.LTS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.helpnserve.LTS.Model.User;
import jp.helpnserve.LTS.Service.GeneralService;

@Controller
public class MainController {

	@Autowired
	GeneralService generalService;

	@RequestMapping({ "/", "/home" })
	public String home() {

		return "/user/mainPage";
	}

	@GetMapping(path = "/admin/signup")
	public String signUpForm(Model model) {

		model.addAttribute("user", new User());
		return "/admin/signup";
	}

	@GetMapping("/api/v1/test-ajax")
	@ResponseBody
	public String TestAjax() {
		return "hello ajax";
	}
}